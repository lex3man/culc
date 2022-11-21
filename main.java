import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Comparator;
import static java.util.stream.Collectors.toList;


class Culc {

  int x;
  int y;
  char oper;
  int result;

  public Culc(String op1, String op2, char op) {
    this.x = Integer.parseInt(op1);
    this.y = Integer.parseInt(op2);
    this.oper = op;
  }

  int culcRes() {
    int res = 0;
    switch(this.oper) {
      case '+':
        res = this.x + this.y;
        // System.out.println(this.x + this.y);
        break;
      case '*':
        res = this.x * this.y;
        // System.out.println(this.x * this.y);
        break;
      case '-':
        res = this.x - this.y;
        // System.out.println(this.x - this.y);
        break;
      case '/':
        res = this.x / this.y;
        // System.out.println(this.x / this.y);
        break;
    }
    return res;
  }

  enum RomanNumeral {
    I(1), IV(4), V(5), IX(9), X(10), 
    XL(40), L(50), XC(90), C(100);

    private int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static List<RomanNumeral> getReverseSortedValues() {
        return Arrays.stream(values())
          .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
          .collect(toList());
    }
  }

  public static String arabicToRoman(int number) {
    if ((number <= 0) || (number > 100)) {
        throw new IllegalArgumentException(number + " не в диапазоне 0 - 100");
    }

    List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

    int i = 0;
    StringBuilder sb = new StringBuilder();

    while ((number > 0) && (i < romanNumerals.size())) {
        RomanNumeral currentSymbol = romanNumerals.get(i);
        if (currentSymbol.getValue() <= number) {
            sb.append(currentSymbol.name());
            number -= currentSymbol.getValue();
        } else {
            i++;
        }
    }

    return sb.toString();
  }


  public static void operAnalys(String in, char initOp, int count) throws Exception {
    Map<String, String> map = new HashMap<>();
    map.put("I", "1");
    map.put("II", "2");
    map.put("III", "3");
    map.put("IV", "4");
    map.put("V", "5");
    map.put("VI", "6");
    map.put("VII", "7");
    map.put("VIII", "8");
    map.put("IX", "9");
    map.put("X", "10");

    if (initOp == 'x') {
      throw new Exception("т.к. строка не является математической операцией");
    }
    if (count > 1) {
      throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
    }
    String[] ops = in.toUpperCase().split("\\"+initOp);

    if (ops[0].matches("[IVXLCDM]+") & ops[1].matches("[IVXLCDM]+")) {
      ops[0] = map.getOrDefault(ops[0], "0");
      ops[1] = map.getOrDefault(ops[1], "0");
      if (initOp == '-') {
        int x = Integer.parseInt(ops[0]);
        int y = Integer.parseInt(ops[1]);
        if ((ops[0] == "0") | (ops[1] == "0")) {
          throw new Exception("на вход принимаются только числа от 1 до 10 включительно, не более");
        }
        if (x < y) {
          throw new Exception("т.к. в римской системе нет отрицательных чисел");
        }
      }
      Culc res = new Culc(ops[0], ops[1], initOp);
      int r = res.culcRes();
      System.out.println(arabicToRoman(r));
    } else if (!(ops[0].matches("[IVXLCDM]+")) & !(ops[1].matches("[IVXLCDM]+"))) {
      if ((Integer.parseInt(ops[0])<1 | Integer.parseInt(ops[0])>10) | (Integer.parseInt(ops[1])<1 | Integer.parseInt(ops[1])>10)) {
        throw new Exception("на вход принимаются только числа от 1 до 10 включительно, не более");
      }
      Culc res = new Culc(ops[0], ops[1], initOp);
      System.out.println(res.culcRes());
    } else {
      throw new Exception("т.к. используются одновременно разные системы счисления");
    }
  }

  public static void main(String[] args) {
    String stopWord = "exit";
    String input;
    char operator;
    int len;
    int opCount;
    Scanner reader = new Scanner(System.in);

    while (true) {
      // System.out.println("Введите операцию: ");
      input = reader.nextLine();
      if (input.equals(stopWord)) {
        break;
      }
      input = input.replaceAll("\\s+","");
      len = input.length();
      opCount = 0;
      operator = 'x';

      for (int i = 0; i < len; i++) {
        switch(input.charAt(i)) {
          case '+':
            operator = '+';
            opCount++;
            break;

          case '-':
            operator = '-';
            opCount++;
            break;

          case '*': 
            operator = '*';
            opCount++;
            break;

          case '/':
            operator = '/';
            opCount++;
            break;
        }
      }
      try {
        operAnalys(input, operator, opCount);
      } catch (Exception e) {
        System.out.println(e);
      }
    }

    reader.close();
  }
}
