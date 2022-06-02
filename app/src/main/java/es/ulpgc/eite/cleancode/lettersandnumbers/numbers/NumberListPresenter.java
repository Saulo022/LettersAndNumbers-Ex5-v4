package es.ulpgc.eite.cleancode.lettersandnumbers.numbers;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.lettersandnumbers.app.AppMediator;
import es.ulpgc.eite.cleancode.lettersandnumbers.app.LettersToNumbersState;
import es.ulpgc.eite.cleancode.lettersandnumbers.app.NumbersToLettersState;
import es.ulpgc.eite.cleancode.lettersandnumbers.data.NumberData;

public class NumberListPresenter implements NumberListContract.Presenter {

  public static String TAG = NumberListPresenter.class.getSimpleName();

  private WeakReference<NumberListContract.View> view;
  private NumberListState state;
  private NumberListContract.Model model;
  private AppMediator mediator;

  public NumberListPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getNumberListState();
  }


  @Override
  public void onStart() {
    // Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new NumberListState();
    }

    // use passed state if is necessary
    LettersToNumbersState savedState = getStateFromPreviousScreen();
    if (savedState != null) {

      // update the model if is necessary
      state.idLetter = savedState.idLetter;
      state.numeroActual = savedState.ultNumero;

      for(int i=0; i<savedState.numeros.size(); i++){
        if(savedState.numeros.get(i).id == savedState.idLetter){
            NumberData numberData = new NumberData();
            numberData = savedState.numeros.get(i);
            state.datasource.add(numberData);
        }
      }
    }

  }

  @Override
  public void onRestart() {
    // Log.e(TAG, "onRestart()");

    // update the model if is necessary
    model.onRestartScreen(state.data);
  }

  @Override
  public void onResume() {
    // Log.e(TAG, "onResume()");


    /*
    // use passed state if is necessary
    NumberListState savedState = router.getStateFromNextScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromNextScreen(savedState.data);
    }
    */

    // call the model and update the state

    // update the view
    view.get().onDataUpdated(state);

  }

  @Override
  public void onBackPressed() {
    // Log.e(TAG, "onBackPressed()");
    NumbersToLettersState numbers = new NumbersToLettersState();
    numbers.listaDeNumeros = state.datasource;
    numbers.lastNumber = model.lastNumber();
    passStateToPreviousScreen(numbers);
    state.datasource.clear();
  }

  @Override
  public void onPause() {
    // Log.e(TAG, "onPause()");
  }

  @Override
  public void onDestroy() {
    // Log.e(TAG, "onDestroy()");
  }


  /*
  private void navigateToNextScreen() {
    Context context = mediator.getApplicationContext();
    Intent intent = new Intent(context, NumberListActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  private void passStateToNextScreen(NumberListState state) {
    mediator.setNextNumberListScreenState(state);
  }


  private NumberListState getStateFromNextScreen() {
    return mediator.getNextNumberListScreenState();
  }
  */

  private void passStateToPreviousScreen(NumbersToLettersState state) {
    mediator.setPreviousNumberListScreenState(state);
  }

  private LettersToNumbersState getStateFromPreviousScreen() {
    return mediator.getPreviousNumberListScreenState();
  }

  @Override
  public void onClickNumberListCell(NumberData data) {
    // Log.e(TAG, "onClickNumberListCell()");
  }

  @Override
  public void onClickNumberListButton() {
    // Log.e(TAG, "onClickNumberListButton()");
    NumberData numberData = new NumberData();
    state.numeroActual = model.upadateNumber(state.numeroActual);
    numberData.number = state.numeroActual;
    numberData.idLetra = state.idLetter;
    state.datasource.add(numberData);
    view.get().onDataUpdated(state);
  }

  @Override
  public void injectView(WeakReference<NumberListContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(NumberListContract.Model model) {
    this.model = model;
  }

}
