package nikhil.banksuite;

class Launcher {
    public static void main(String args[]) {
        try {
            App.secondMain(args);
        } catch (Exception e) {
            new ExceptionDialog("Error occured" ,e).show();
        }
    }
}