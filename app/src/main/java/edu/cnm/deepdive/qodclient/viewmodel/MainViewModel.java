package edu.cnm.deepdive.qodclient.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import edu.cnm.deepdive.qodclient.model.Quote;
import edu.cnm.deepdive.qodclient.service.QodService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

  private MutableLiveData<Quote> random;
  private CompositeDisposable pending = new CompositeDisposable();
  private  MutableLiveData<List<Quote>> search;

  public MainViewModel(@NonNull Application application) {
    super(application);
  }

  public LiveData<Quote> getRandomQuote() {
    if (random == null) {
      random = new MutableLiveData<>();
    }
    pending.add(
        QodService.getInstance().random().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe((quote) -> random.setValue(quote))
    );
    return random;
  }

  public LiveData<List<Quote>> getSearchQuote(String string) {
    if(search == null){
      search = new MutableLiveData<>();
    }
      pending.add(
          QodService.getInstance().search(string).subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread()).subscribe((quote) -> search.setValue(quote))
      );
     return search;
  }


}
