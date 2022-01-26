package todoapp.io;

import todoapp.io.interfaces.InputReader;

import java.util.Scanner;

public class ConsoleReader implements InputReader {

  private Scanner reader;

  public ConsoleReader() {
    this.reader = new Scanner(System.in);
  }

  @Override
  public String readLine() {
    return this.reader.nextLine();
  }
}
