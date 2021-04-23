package atm_system;
import java.util.ArrayList;
import java.util.Scanner;

class SystemUser {

    private String UN, PW, BALANCE;
    private boolean ROLE;

    public SystemUser(String un, String pw, String balance, boolean role) {
        this.UN = un;
        this.PW = pw;
        this.BALANCE = balance;
        this.ROLE = role;
    }

    public String getUN() {
        return UN;
    }

    public void setUN(String newUN) {
        UN = newUN;
    }

    public String getPW() {
        return PW;
    }

    public void setPW(String newPW) {
        PW = newPW;
    }

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String newBALANCE) {
        BALANCE = newBALANCE;
    }

    public boolean getROLE() {
        return ROLE;
    }
}

public class ATM_SYSTEM {

    public static Scanner input = new Scanner(System.in);
    public static ArrayList<SystemUser> Users = new ArrayList();

    public static boolean checkAccount(String number, String password) {
        for (int i = 0; i < Users.size(); i++) {
            if (number.equals(Users.get(i).getUN()) && password.equals(Users.get(i).getPW())) {
                return true;
            }
        }
        return false;
    }

    public static int admin_menu() {
        int menuChoice;
        do {
            System.out.print("Choose Options :"
                    + "\n 1. Adding User  2. Show Users  3. Quit\n\n");
            menuChoice = input.nextInt();

            if (menuChoice < 1 || menuChoice > 3) {
                System.out.println("please try again ...");
            }

        } while (menuChoice < 1 || menuChoice > 3);

        return menuChoice;
    }

    public static int user_menu() {
        int menuChoice;
        do {
            System.out.print("\nChoose Options:"
                    + "\n 1. Your Balance 2. Deposit process"
                    + "3. Withdraw process 4. Quit\n\n");
            menuChoice = input.nextInt();

            if (menuChoice < 1 || menuChoice > 4) {
                System.out.println("please try again!");
            }

        } while (menuChoice < 1 || menuChoice > 4);

        return menuChoice;
    }

    public static void displayBalance(String number) {
        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).getUN().equals(number)) {
                String displayBalance = Users.get(i).getBALANCE();
                System.out.println("\nYour Balance is $" + displayBalance);
            }
        }
    }

    public static void deposit(double x, double y, String number) {
        double depositAmt = x, currentBal = y;
        double newBalance = depositAmt + currentBal;

        System.out.printf("Your Balance is $%.2f\n", newBalance);
        for (int i = 0; i < Users.size(); i++) {
            if (number.equals(Users.get(i).getUN())) {
                Users.get(i).setBALANCE(newBalance + "");
            }
        }
    }

    public static void withdraw(double x, double y, String number) {
        double withdrawAmt = y, currentBal = x;
        double newBalance = withdrawAmt - currentBal;
        System.out.printf("Your Balance is %.2f\n", newBalance);
        for (int i = 0; i < Users.size(); i++) {
            if (number.equals(Users.get(i).getUN())) {
                Users.get(i).setBALANCE(newBalance + "");
            }
        }
    }

    public static boolean checkAvailable(String number) {
        for (int i = 0; i < Users.size(); i++) {
            if (number.equals(Users.get(i).getUN())) {
                return true;
            }
        }
        return false;
    }

    public static void addNewUser() {
        boolean flag;
        String account_num, account_pw, account_role;
        do {
            System.out.println("Number of Account : ");
            account_num = input.next();
            System.out.println("Password: ");
            account_pw = input.next();
            System.out.println("user's is admin <yes or no> : ");
            account_role = input.next();
            flag = checkAvailable(account_num);
            if (flag == false) {
                Users.add(new SystemUser(account_num, account_pw, "0.0", account_role.equals("YES") || account_role.equals("yes") ? true : false));
            } else {
                System.out.println("account number is existing, plaese try again!");
            }
        } while (flag == true);
    }

    public static void main(String[] args) {

        Users.add(new SystemUser("55-2222", "alanood", "0.0", true));
        Users.add(new SystemUser("55-1111", "alanood", "0.0", false));

        String accountNumber, accountPassword;
        int menuOption = 0;

        double depositAmt = 0, withdrawAmt = 0, currentBalance = 0;

        boolean isAccountAccepted = false;
        boolean AccountIsAdmin = false;

        do {
            System.out.println("write Your Number: ");
            accountNumber = input.next();

            System.out.println("Password: ");
            accountPassword = input.next();

            isAccountAccepted = checkAccount(accountNumber, accountPassword);

            if (isAccountAccepted == true) {
                System.out.println("\nHello Mr -> " + accountNumber +"\n");
            } else {
                System.out.println("\nusername or password incorrect, please try again ...\n");
            }
        } while (isAccountAccepted == false);

        for (int i = 0; i < Users.size(); i++) {
            if (accountNumber.equals(Users.get(i).getUN()) && Users.get(i).getROLE() == true) {
                AccountIsAdmin = true;
                break;
            } else {
                AccountIsAdmin = false;
            }
        }

        if (AccountIsAdmin == true) {
            while (menuOption != 3) {
                menuOption = admin_menu();
                switch (menuOption) {
                    case 1:
                        addNewUser();
                        break;
                    case 2:
                        for (int i = 0; i < Users.size(); i++) {
                            System.out.println(Users.get(i).getUN() + " " + Users.get(i).getPW() + " " + Users.get(i).getBALANCE() + " " + Users.get(i).getROLE());
                        }
                        break;
                    case 3:
                        System.exit(0);
                        break;
                }
            }
        } else {
            while (menuOption != 4) {
                menuOption = user_menu();
                switch (menuOption) {
                    case 1:
                        displayBalance(accountNumber);
                        break;
                    case 2:
                        System.out.print("\nEnter Amount: $ ");
                        depositAmt = input.nextDouble();

                        for (int i = 0; i < Users.size(); i++) {
                            if (accountNumber.equals(Users.get(i).getUN())) {
                                currentBalance = Double.parseDouble(Users.get(i).getBALANCE());
                            }
                        }
                        deposit(depositAmt, currentBalance, accountNumber);
                        break;
                    case 3:
                        System.out.print("\nEnter Amount: $ ");
                        withdrawAmt = input.nextDouble();

                        for (int i = 0; i < Users.size(); i++) {
                            if (accountNumber.equals(Users.get(i).getUN())) {
                                currentBalance = Double.parseDouble(Users.get(i).getBALANCE());
                            }
                        }
                        while (withdrawAmt > currentBalance) {
                            System.out.print("\nyour current balance lessthan withdraw, please try again ...\n");
                            System.out.print("\nEnter Amount: $ ");
                            withdrawAmt = input.nextDouble();
                        }
                        withdraw(withdrawAmt, currentBalance, accountNumber);
                        break;
                    case 4:
                        System.exit(0);
                        break;
                }
            }
        }
    }
}


