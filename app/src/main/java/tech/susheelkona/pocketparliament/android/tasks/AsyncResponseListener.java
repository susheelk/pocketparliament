package tech.susheelkona.pocketparliament.android.tasks;

/**
 * @author Susheel Kona
 */

public interface AsyncResponseListener<T> {
    void onTaskSuccess(Class source, T data);
    void onTaskError(Class source, String message);
}
