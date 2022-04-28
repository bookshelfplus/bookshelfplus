package plus.bookshelf;

public class PasswordTest {
    // 密码长度 8-16 位，可以包含大写字母、小写字母、数字或特殊符号中的任意三种
    public static final String password = "[A-Za-z0-9_!@#]{8,16}$";

    public static void main(String[] args) {
        String password1 = "ABCDEFGHIG";  //全部大写
        String password2 = "abcdefghig";  //全部小写
        String password3 = "0123456789";  //全部数字
        String password4 = "!@#$%^&*()";  //全部特殊字符
        String password5 = "ABCDEabcde";  //大写和小写
        String password6 = "ABCDE01234";  //大写和数字
        String password7 = "ABCDE!@#$%";  //大写和特殊字符
        String password8 = "abcde01234";  //小写和数字
        String password9 = "abcde!@#$%";  //小写字母和特殊字符
        String password10 = "01234!@#$%"; //数字和特殊字符
        String password11 = "Aa4!";       //长度不够8位数
        String password12 = "ABCDE01234!@#$%"; //符合要求密码任意三种
        String password13 = "ABCDEabcde!@#$%"; //符合要求密码任意三种
        String password14 = "ABCDEabcde01234"; //符合要求密码任意三种
        String password15 = "abcde01234!@#$%"; //符合要求密码任意三种
        String password16 = "ABCabc012@#"; //符合要求密码任意三种 和 符合全部的四种
        String password17 = "abcde01234_-!@#";

        System.out.println(password1.matches(password) + " 1");
        System.out.println(password2.matches(password) + " 2");
        System.out.println(password3.matches(password) + " 3");
        System.out.println(password4.matches(password) + " 4");
        System.out.println(password5.matches(password) + " 5");
        System.out.println(password6.matches(password) + " 6");
        System.out.println(password7.matches(password) + " 7");
        System.out.println(password8.matches(password) + " 8");
        System.out.println(password9.matches(password) + " 9");
        System.out.println(password10.matches(password) + " 10");
        System.out.println(password11.matches(password) + " 11");
        System.out.println(password12.matches(password) + " 12");
        System.out.println(password13.matches(password) + " 13");
        System.out.println(password14.matches(password) + " 14");
        System.out.println(password15.matches(password) + " 15");
        System.out.println(password16.matches(password) + " 16");
        System.out.println(password17.matches(password) + " 17");
    }
}