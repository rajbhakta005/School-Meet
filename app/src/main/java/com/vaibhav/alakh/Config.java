package com.vaibhav.alakh;

public class Config {

    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "pgpapp";


    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    //This would be the name of our shared preferences for Registration
    public static final String SHARED_PREF_MOBILE = "mobilesp";
    public static final String MOBILE_REG_SP = "mobile";

    //This would be used to store the email of current logged in user
    public static final String MID_SHARED_PREF = "email";

    public static final String KEY_MID = "membership_id";
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_NAME = "name";
    public static final String KEY_PAYMENT_STATUS_HOME = "payment_status";
    public static final String KEY_APP_VERSION = "app_version";
    public static final String KEY_APP_PGP1 = "rajneeti";
    public static final String KEY_APP_PGP2 = "jansankhya";
    public static final String KEY_APP_PGP3 = "nayikheti";
    public static final String KEY_APP_PGP4 = "entrepreneur";
    public static final String KEY_APP_PGP5 = "mahilao";
    public static final String KEY_APP_PGP6 = "beghar";
    public static final String KEY_APP_PGP7 = "jalparyavaran";

    public static final String KEY_APP_ASANGATHIT = "asangathit";
    public static final String KEY_APP_ENTREPRENEURSHIP = "entrepreneurship";
    public static final String PROFILE_IMAGE_URL = "member_photo";
    public static final String JSON_ARRAY = "result";

    public static final String DATA_URL = "https://seekho.xyz/pgp-android/api/get-user-data.php?membership_id=";


    //For Digital ID Search
    public static final String TEXT_MID = "membership_id";
    public static final String TEXT_MOBILE = "mobile";
    public static final String TEXT_NAME = "name";
    public static final String TEXT_WARD = "ward";
    public static final String TEXT_ADDRESS = "address";
    public static final String TEXT_ASSEMBLY = "assembly";
    public static final String TEXT_OCCUPATION = "occupation";
    public static final String TEXT_CITY = "city";
    public static final String TEXT_PGP = "rajneeti";
    public static final String TEXT_ASANGATHIT = "asangathit";
    public static final String TEXT_ENTREPRENEURSHIP = "entrepreneurship";
    public static final String TEXT_PAYMENT_STATUS = "payment_status";
    public static final String TEXT_PROFILE_IMAGE_URL = "member_photo";
    public static final String SEARCH_DIGITAL_ID_URL = "https://seekho.xyz/pgp-android/api/search-digital-id.php?membership_id=";

    public static final String SEARCH_PAYMENT_DETAILS = "https://seekho.xyz/pgp-android/api/get-payment-data.php?membership_id=";


    // Directory name to store captured images and videos
    public static final String IMAGE_DIRECTORY_NAME = "Pictures";

    // File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL = "https://seekho.xyz/pgp-android/api/add-member.php";

    // File upload url (replace the ip with your server address)
    public static final String FILE_UPLOAD_URL_LEAP = "https://seekho.xyz/pgp-android/leap/api/add-member.php";

    // File upload url (replace the ip with your server address)
    public static final String VERIFY_LEAP_MID = "https://seekho.xyz/pgp-android/api/confirm-leap.php";


    // Change Profile URL(replace the ip with your server address)
    public static final String CHANGE_PROFILE_URL = "https://seekho.xyz/pgp-android/api/change-photo.php";

    // Referral Data URL
    public static final String REFERRAL_URL = "https://seekho.xyz/pgp-android/api/referral-data.php";

    public static final String PAYMENT_MID = "";
    public static final String PAYMENT_MOBILE = "";

    public static final String PAYMENT_STATUS = "";

    public static final String USER_MOBILE = "";

    public static final String NOTIFICATION_SHARED_PREF = "";

    //For Payment Pay Activity
    public static final String PAYMENT_PAY_MID = "membership_id";
    public static final String PAYMENT_PAY_MOBILE = "mobile";



}
