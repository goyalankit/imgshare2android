package com.example.ankit.imgshare2android;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.AccountPicker;

public class AuthActivity extends Activity {
    private static final int PICK_ACCOUNT_REQUEST = 1241;
    protected AccountManager accountManager;
    private Account selectedAccount;
    protected Intent intent;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent googlePicker = AccountPicker.newChooseAccountIntent(selectedAccount, null,
                new String[]{GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE}, true, null, null, null, null) ;
        intent = googlePicker;
        startActivityForResult(googlePicker,PICK_ACCOUNT_REQUEST);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == PICK_ACCOUNT_REQUEST && resultCode == RESULT_OK) {
            accountManager = AccountManager.get(getApplicationContext());
            Account[] accounts = accountManager.getAccountsByType("com.google");
            String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);

            for (Account account : accounts) {
                if (account.name.equals(accountName))
                    selectedAccount = account;
            }

            Intent intent = new Intent(this, AuthHandler.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra("account", selectedAccount);
            startActivity(intent);
        }
    }


}