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
import java.util.LinkedList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

  private MutableLiveData<Quote> random;
  private CompositeDisposable pending = new CompositeDisposable();
  private  MutableLiveData<List<Quote>> searchList;

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

  public LiveData<List<Quote>> getSearchQuote(String search) {
    if(searchList == null){
      searchList = new MutableLiveData<>();
    }
    if (search != null) {
      pending.add(
          QodService.getInstance().search(search).subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe((quote) -> searchList.setValue(quote))
      );
    }else {
      searchList.setValue(new LinkedList<>());
    }
     return searchList;




}
