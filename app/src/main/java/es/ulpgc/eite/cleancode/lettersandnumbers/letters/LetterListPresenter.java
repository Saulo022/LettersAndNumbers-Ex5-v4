package es.ulpgc.eite.cleancode.lettersandnumbers.letters;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.lettersandnumbers.app.AppMediator;
import es.ulpgc.eite.cleancode.lettersandnumbers.app.LettersToNumbersState;
import es.ulpgc.eite.cleancode.lettersandnumbers.app.NumbersToLettersState;
import es.ulpgc.eite.cleancode.lettersandnumbers.data.LetterData;
import es.ulpgc.eite.cleancode.lettersandnumbers.data.NumberData;

public class LetterListPresenter implements LetterListContract.Presenter {

  public static String TAG = LetterListPresenter.class.getSimpleName();

  private WeakReference<LetterListContract.View> view;
  private LetterListState state;
  private LetterListContract.Model model;
  private AppMediator mediator;

  public LetterListPresenter(AppMediator mediator) {
    this.mediator = mediator;
    state = mediator.getLetterListState();
  }

  @Override
  public void onStart() {
    // Log.e(TAG, "onStart()");

    // initialize the state if is necessary
    if (state == null) {
      state = new LetterListState();
    }

    /*
    // use passed state if is necessary
    LetterListState savedState = router.getStateFromPreviousScreen();
    if (savedState != null) {

      // update the model if is necessary
      model.onDataFromPreviousScreen(savedState.data);
    }
    */

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

    // use passed state if is necessary
    NumbersToLettersState savedState = getStateFromNextScreen();
    if (savedState != null) {

      // update the model if is necessary
      for (int i=0; i<savedState.listaDeNumeros.size(); i++){
        state.listaDeNumeros.add(savedState.listaDeNumeros.get(i));
      }
      state.lastNumber = savedState.lastNumber;
    }


    // call the model and update the state
    state.data = model.getStoredData();

    // update the view
    view.get().onDataUpdated(state);

  }

  @Override
  public void onBackPressed() {
    // Log.e(TAG, "onBackPressed()");
  }

  @Override
  public void onPause() {
    // Log.e(TAG, "onPause()");
  }

  @Override
  public void onDestroy() {
    // Log.e(TAG, "onDestroy()");
  }


  private void passStateToNextScreen(LettersToNumbersState state) {
    mediator.setNextLetterListScreenState(state);
  }

  /*
  private void passStateToPreviousScreen(LetterListState state) {
    mediator.setPreviousLetterListScreenState(state);
  }

  private LetterListState getStateFromPreviousScreen() {
    return mediator.getPreviousLetterListScreenState();
  }
  */

  private NumbersToLettersState getStateFromNextScreen() {
    return mediator.getNextLetterListScreenState();
  }

  @Override
  public void onClickLetterListCell(LetterData data) {
    // Log.e(TAG, "onClickLetterListCell()");
    LettersToNumbersState letters = new LettersToNumbersState();
    letters.idLetter = data.id;
    letters.ultNumero = state.lastNumber;
    letters.numeros = state.listaDeNumeros;
    passStateToNextScreen(letters);
    view.get().navigateToNextScreen();
  }

  @Override
  public void onClickLetterListButton() {
    // Log.e(TAG, "onClickLetterListButton()");
    LetterData letterData = new LetterData();
    state.data = model.updateLetter(state.data);
    letterData.letter = state.data;
    state.datasource.add(letterData);
    view.get().onDataUpdated(state);
  }

  @Override
  public void injectView(WeakReference<LetterListContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(LetterListContract.Model model) {
    this.model = model;
  }

}