package src.net.idscan;

import com.mmm.readers.ErrorCode;
import com.mmm.readers.ErrorHandler;

public class MyErrorHandler implements ErrorHandler {

    @Override
    public void OnMMMReaderError(ErrorCode errorCode, String s) {
        System.out.println("Error in IDScanner: " + errorCode.toString() + ", " + s);
    }
}
