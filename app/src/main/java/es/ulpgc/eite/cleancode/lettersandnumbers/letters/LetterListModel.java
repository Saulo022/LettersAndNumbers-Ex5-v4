package es.ulpgc.eite.cleancode.lettersandnumbers.letters;

import android.util.Log;

import java.util.List;

import es.ulpgc.eite.cleancode.lettersandnumbers.data.NumberData;

public class LetterListModel implements LetterListContract.Model {

  public static String TAG = LetterListModel.class.getSimpleName();

  private String data;
  private char letra='A';
  private NumberData ultimoDato;
  private int j=0;

  public LetterListModel(String data) {
    this.data = data;
  }

  @Override
  public String getStoredData() {
    // Log.e(TAG, "getStoredData()");
    return data;
  }

  @Override
  public void onRestartScreen(String data) {
    // Log.e(TAG, "onRestartScreen()");
  }

  @Override
  public void onDataFromNextScreen(String data) {
    // Log.e(TAG, "onDataFromNextScreen()");
  }

  @Override
  public void onDataFromPreviousScreen(String data) {
    // Log.e(TAG, "onDataFromPreviousScreen()");
  }
  @Override
  public String updateLetter(String letter){
    letter = String.valueOf(letra);
    letra++;
    return letter;
  }
  @Override
  public int ultNumber(List<NumberData> numberData){
    return j;
  }
}
