package todoapp;

import todoapp.io.ConsoleReader;
import todoapp.io.ConsoleWriter;
import todoapp.io.interfaces.InputReader;
import todoapp.io.interfaces.OutputWriter;
import todoapp.views.HomeView;

public class TodoApp {

  public static void main(String[] args) {

    InputReader reader = new ConsoleReader();
    OutputWriter writer = new ConsoleWriter();
    HomeView homeView = new HomeView();

    homeView.getLogin(reader, writer);
  }
}
