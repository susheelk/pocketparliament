package tech.susheelkona.pocketparliament.android.background;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.work.Worker;
import androidx.work.WorkerParameters;
import tech.susheelkona.pocketparliament.R;
import tech.susheelkona.pocketparliament.android.tasks.SharedPreferenceHelper;
import tech.susheelkona.pocketparliament.model.NewsItem;
import tech.susheelkona.pocketparliament.model.legislation.Vote;
import tech.susheelkona.pocketparliament.services.BillService;
import tech.susheelkona.pocketparliament.services.NewsService;

public class BackgroundFetchWorker extends Worker {

    private final NewsService newsService = NewsService.getInstance();
    private final BillService billService = BillService.getInstance();
    private final SharedPreferenceHelper preferences = SharedPreferenceHelper.getInstance();


    private Context context;

    public BackgroundFetchWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        List<String> followedBills = preferences.getFollowedBills(getApplicationContext());
        List<String> followedMps = preferences.getFollowedMemberParliaments(getApplicationContext());
        int lastVote = preferences.getLastVote(getApplicationContext());
        String lastNews = preferences.getLastNews(getApplicationContext());

        List<NewsItem> newsItems = new ArrayList<>();

        if (!followedBills.isEmpty() || followedBills == null){
            try {
                newsItems = newsService.get("");
                if (lastNews.matches("EMPTY")){
                    preferences.storeLastCheck(0, newsItems.get(1).getTagline(), getApplicationContext());
                    return Result.retry();

                } else {
                    NewsItem last = new NewsItem();
                    last.setTagline(lastNews);
                    int index = newsItems.size();
                    if (newsItems.contains(last)) {
                        index = newsItems.indexOf(last);
                    }
                    newsItems = newsItems.subList(0, index);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return Result.failure();
            }
        }
        pushNotifications(newsItems, null);
        if (!newsItems.isEmpty()){
            preferences.storeLastCheck(0, newsItems.get(0).getTagline(), getApplicationContext());
        }
        return Result.success();
    }

    private void pushNotifications(List<NewsItem> newsItems, List<Vote> votes){
        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        if (!newsItems.isEmpty()){
            for (NewsItem news: newsItems){
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(),
                        getApplicationContext().getString(R.string.news_notification_chanel))
                        .setContentTitle("Bill "+news.getBillNumber())
                        .setGroup(getApplicationContext().getString(R.string.news_notification_chanel))
                        .setContentText(news.getTagline()+" on "+news.getDate())
                        .setSmallIcon(R.drawable.ic_notif_bill);
                manager.notify(new Random().nextInt(), mBuilder.build());
            }
        }
    }
}

