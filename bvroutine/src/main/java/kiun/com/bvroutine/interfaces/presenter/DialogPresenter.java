package kiun.com.bvroutine.interfaces.presenter;

public interface DialogPresenter {

    void configDialog(String title, Object... args);

    void showMessage(String msg, Object... values);

    void show();

    void hide();
}
