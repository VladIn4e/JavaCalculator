import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class prog14 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        okno nf = new okno();
    }
}


class okno extends JFrame {
    private JTextArea text;

    // где будет храниться результат операции
    private double result = 0;

    // хранить знак операции (плюс, минус и т.д.)
    private String userOperation = "";

    // предусмотрены операции только с двумя числами
    private double num1=0, num2=0;

    // флаг точки, в состоянии True говорит, что точка уже поставлено и запрещает ставить вторую точку
    private boolean dot_entered = false;

    // флаг сброса результата вычесления, при нажатии любой цифры
    private boolean flashResult = false;


    // метод который будет делать вычесления
    public double getResult(double num1, double num2, String userOperation) {
        if (userOperation.equals("+")) result = num1+num2;
        if (userOperation.equals("-")) result = num1-num2;
        if (userOperation.equals("*")) result = num1*num2;
        if (userOperation.equals("/")) result = num1/num2;
        flashResult = true;
        dot_entered = false;
        return result;
    }

    private void btnClick(JButton btn) {

        // точка входа программы :: str хранит последний введенный символ
        String str = btn.getText();

        // JOptionPane.showMessageDialog(null, str);
        if (str.equals("Выход")) System.exit(0);

        // если нажата клавиша "C", сбрасываем все настройки "по умолчанию"
        if (str.equals("C")) {
            num1=num2=0;
            dot_entered = false;
            text.setText("0");
            text.setCaretPosition(1);
        }

        // если получаем точку, устанавливаем флаг точки в True
        else if (str.equals(".")) {
            if (!dot_entered) {
                text.setText(""+text.getText()+str);
                dot_entered = true;
            }
            if (flashResult == true) {
                flashResult = false;
                text.setText("0.");
                text.setCaretPosition(2);
            }
        }

        // если знаки операций, то вызвать функцию, в которую передать значение из массива
        else if (str.equals("+") | str.equals("-") | str.equals("/") | str.equals("*")) {
            num1 = Double.parseDouble(text.getText());
            userOperation = str;
            text.setText("0");
            text.setCaretPosition(1);
            dot_entered = false;
        }

        // провести операцию с двумя числами, вывести результат на панель калькулятора
        else if (str.equals("=")) {
            num2 = Double.parseDouble(text.getText());
            text.setText(Double.toString(getResult(num1, num2, userOperation)));
        }

        else {
            if (text.getText().equals("0") | flashResult == true) {
                flashResult = false;
                text.setText(str);
            }
            else {
                text.setText(""+text.getText()+str);
            }
        }
    }


    public okno() {
        Container cont = getContentPane();
        JPanel pan = new JPanel();
        pan.setLayout(null);
        Font btnFont = new Font("serid",0,20);
        Font labFont = new Font("arial",1,30);
        Font textFont = new Font("arial",2,30);
        JButton[] btn = new JButton[18];

        for(int i=0;i<18;i++) {
            btn[i] = new JButton();
            btn[i].setSize(100, 25);
            btn[i].setFont(btnFont);
            btn[i].setLocation(30, 50 + i*30);
            btn[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    btnClick((JButton)e.getSource());
                }
            });
            pan.add(btn[i]);
        }

        for (int i=0;i<10;i++) btn[i].setText(""+i);
        btn[10].setText("+");
        btn[11].setText("-");
        btn[12].setText("/");
        btn[13].setText("*");
        btn[14].setText(".");
        btn[15].setText("=");
        btn[16].setText("C");
        btn[17].setText("Выход");
        JLabel lab = new JLabel("Результат: ");
        lab.setFont(labFont);
        lab.setBounds(130, 0, 300, 50);
        pan.add(lab);
        text = new JTextArea();
        text.setFont(textFont);
        text.setBounds(300, 10, 300, 35);
        text.setForeground(new Color(0,0,100));
        text.setBackground(Color.WHITE);
        text.setText("0");
        text.setCaretPosition(1);
        pan.add(text);
        pan.add(lab);
        cont.add(pan);
        setBounds(0,0, 600, 630);
        setTitle("Калькулятор");
        setVisible(true);
    }
}