package tech.susheelkona.pocketparliament.android.tasks;

import android.util.Log;

import tech.susheelkona.pocketparliament.model.NewsItem;
import tech.susheelkona.pocketparliament.services.NewsService;

import java.util.List;

public class GetNewsTask extends AbstractAsyncTask<String, Void, List<NewsItem>> {

    private final NewsService service = NewsService.getInstance();
    private boolean error = false;

    public GetNewsTask(){
        super();
    }

    @Override
    protected List<NewsItem> doInBackground(String... args) {
        try {
            return service.get(args[0]);
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
            error = true;
//            getAsyncResponseListener().onTaskError(this.getClass(), e.getMessage());
        }
        return null;
    }
    
}
