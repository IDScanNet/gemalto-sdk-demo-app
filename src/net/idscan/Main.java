package net.idscan;


import com.mmm.readers.ErrorCode;
import com.mmm.readers.FullPage.*;
import com.mmm.readers.modules.rfid.CertificateHandler;
import net.idscan.dlparser.DLParser;
import java.util.Arrays;

public class Main  {


    private static final String _KEY = "";


    public static void main(String[] args) {

        Main m = new Main();

        StringBuffer sb = new StringBuffer();
        MyErrorHandler eh = new MyErrorHandler();

        Reader r = m.initialiseScanner(eh);

        r.GetConfigDir(sb);
        System.out.println(sb.toString());

        ReaderState state = r.GetState();
        System.out.println(state.toString());

        r.EnablePlugin("PDF417", true);

        boolean[] isEnabled = new boolean[1];
        r.IsPluginEnabled("PDF417", isEnabled);
        System.out.println(isEnabled.toString());

        StringBuffer stringBuff = new StringBuffer();
        r.GetPluginName(stringBuff, 1);
        System.out.println(stringBuff.toString());

        int featureCount =  r.GetPluginFeatureCount("PDF417");
        System.out.println("Feature Count : " + featureCount );

        m.readDocument(r);
    }


    public Reader initialiseScanner(MyErrorHandler eh) {
        try {
            Reader reader = new Reader();
            reader.EnableLogging(true, 1, -1, "IDScanner.log");

            ErrorCode errorCode = reader.Initialise((DataHandler)null, (EventHandler)null, eh, (CertificateHandler)null, true, false, 0);

            if (errorCode != ErrorCode.NO_ERROR_OCCURRED) {
                System.out.println("Initialise error: " + errorCode.toString());
            } else {
                System.out.println("Initialise successful");
            }
            return reader;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void readDocument(Reader reader) {
        ErrorCode errorCode = reader.ReadDocument();
        String mrzData;


        if (errorCode == ErrorCode.NO_ERROR_OCCURRED) {
//            byte[] rawMRZBytes = new byte[200];
//            int[] rawMRZInts = new int[]{200};
//
//            if (reader.GetData(DataType.CD_CODELINE, rawMRZBytes, rawMRZInts) == ErrorCode.NO_ERROR_OCCURRED) {
//                mrzData = new String(rawMRZBytes, 0, rawMRZInts[0] - 1);
//                parseReaderString(mrzData);
//            }


            PluginDataFeature pluginData = new PluginDataFeature();
            reader.FillPluginDataFeature("PDF417" , 0, pluginData);

            System.out.println(pluginData.toString());
        }
        reader.Shutdown();
    }


    private String parseReaderString(String readerString) {
        DLParser parser = new DLParser();
        try {
            System.out.println("Parser Version: " + parser.getVersion());
            parser.setup(_KEY);

            DLParser.DLResult res = parser.parse(readerString.getBytes("UTF8"));
            //print result.
            System.out.println("Full name: " + res.fullName);
            System.out.println("First name: " + res.firstName);
            System.out.println("Middle name: " + res.middleName);
            System.out.println("Last name: " + res.lastName);
            System.out.println("Name suffix: " + res.nameSuffix);
            System.out.println("Name prefix: " + res.namePrefix);
            System.out.println("Document type: " + res.documentType);
            System.out.println("Country code: " + res.countryCode);
            System.out.println("Country: " + res.country);
            System.out.println("Jurisdiction code: " + res.jurisdictionCode);
            System.out.println("IIN: " + res.iin);
            System.out.println("Address1: " + res.address1);
            System.out.println("Address2: " + res.address2);
            System.out.println("City: " + res.city);
            System.out.println("License number: " + res.licenseNumber);
            System.out.println("Classification code: " + res.classificationCode);
            System.out.println("Restriction code: " + res.restrictionCode);
            System.out.println("Restriction code description: " + res.restrictionCodeDescription);
            System.out.println("Endorsements code: " + res.endorsementsCode);
            System.out.println("Endorsement code description: " + res.endorsementCodeDescription);
            System.out.println("Expiration date: " + res.expirationDate);
            System.out.println("HAZMATExpDate: " + res.HAZMATExpDate);
            System.out.println("Birthdate: " + res.birthdate);
            System.out.println("Card revision date: " + res.cardRevisionDate);
            System.out.println("Gender: " + res.gender);
            System.out.println("Issue date: " + res.issueDate);
            System.out.println("Issue by: " + res.issuedBy);
            System.out.println("Postal code: " + res.postalCode);
            System.out.println("Eye color: " + res.eyeColor);
            System.out.println("Race: " + res.race);
            System.out.println("Hair color: " + res.hairColor);
            System.out.println("Height: " + res.height);
            System.out.println("WeightKG: " + res.weightKG);
            System.out.println("WeightLBS: " + res.weightLBS);
            System.out.println("Compliance type: " + res.complianceType);

            if(res.isLimitedDurationDocument == DLParser.DLResult.FLAG_TRUE)
                System.out.println("Limited duration document: " + "yes");
            else if(res.isLimitedDurationDocument == DLParser.DLResult.FLAG_FALSE)
                System.out.println("Limited duration document: " + "no");
            else
                System.out.println("Limited duration document: " + "undefined");

            if(res.isOrganDonor == DLParser.DLResult.FLAG_TRUE)
                System.out.println("Organ donor: " + "yes");
            else if(res.isOrganDonor == DLParser.DLResult.FLAG_FALSE)
                System.out.println("Organ donor: " + "no");
            else
                System.out.println("Organ donor: " + "undefined");

            if(res.isVeteran == DLParser.DLResult.FLAG_TRUE)
                System.out.println("Veteran: " + "yes");
            else if(res.isVeteran == DLParser.DLResult.FLAG_FALSE)
                System.out.println("Veteran: " + "no");
            else
                System.out.println("Veteran: " + "undefined");

            System.out.println("Vehicle class code: " + res.vehicleClassCode);
            System.out.println("Vehicle class code description: " + res.vehicleClassCodeDescription);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
