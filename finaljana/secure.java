package finaljana;

public class secure {
    private static int failedAttempts = 0; 
    private static long lockTime = 0; 
    private static final int MAX_ATTEMPTS = 3; 
    private static final long LOCK_DURATION = 60_000;

    public static boolean isAccountLocked() {
        if (lockTime == 0) return false; 
        long timeSinceLock = System.currentTimeMillis() - lockTime;
        if (timeSinceLock > LOCK_DURATION) {
            lockTime = 0; 
            failedAttempts = 0; 
            return false;
        }
        return true; 
    }

    public static void registerFailedAttempt() {
        failedAttempts++;
        if (failedAttempts >= MAX_ATTEMPTS) {
            lockTime = System.currentTimeMillis(); 
            System.out.println("Account locked due to too many failed attempts. Try again later.");
        }
    }

   
    public static void resetLoginAttempts() {
        failedAttempts = 0;
        lockTime = 0; 
    }
}
