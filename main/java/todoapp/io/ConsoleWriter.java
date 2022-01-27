package todoapp.io;


import todoapp.io.interfaces.OutputWriter;

public class ConsoleWriter implements OutputWriter {
  @Override
  public void writeLine(String text) {
    System.out.print(text);
  }

  @Override
  public void writeOnNewLine(String text) {
    System.out.println(text);
  }

}
