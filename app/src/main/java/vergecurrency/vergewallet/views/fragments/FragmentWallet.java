package vergecurrency.vergewallet.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.helpers.CurrenciesUtils;
import vergecurrency.vergewallet.models.dataproc.PreferencesManager;
import vergecurrency.vergewallet.structs.Currency;
import vergecurrency.vergewallet.views.fragments.beans.ItemData;
import vergecurrency.vergewallet.views.fragments.walletcards.FragmentTransactionsCard;
import vergecurrency.vergewallet.views.fragments.walletcards.FragmentWalletCard;


public class FragmentWallet extends Fragment {

    public View rootView;
    private String currencyCode;
    private PreferencesManager pm;
    private ViewPager pager;
    private TextView balanceLabel;
    private TextView balanceAmount;
    private TextView changeLabel;
    private TextView changeAmount;


    public FragmentWallet() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pm = new PreferencesManager(getContext());
        currencyCode  = Currency.getCurrencyFromJson(pm.getSelectedCurrency()).getCurrency();

        // Inflate the layout for this fragment

        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_wallet, container, false);

            //Instantiate the ViewPager to have the fragments into the viewpager layout
            pager = (ViewPager) rootView.findViewById(R.id.wallet_cards_viewpager);
			balanceLabel = (TextView) rootView.findViewById(R.id.wallet_card_balance_label);
			balanceAmount= (TextView) rootView.findViewById(R.id.wallet_card_balance);
			changeLabel = (TextView) rootView.findViewById(R.id.wallet_card_change_label);
			changeAmount = (TextView) rootView.findViewById(R.id.wallet_card_change);


            initComponents();
        }
        return rootView;
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {

            switch (pos) {

                case 0:
                   //return FragmentWalletCard.newInstance(new ItemData("Recent transactions", R.drawable.icon_transactions), R.drawable.icon_transactions);
                   return FragmentTransactions.instantiate(getContext(),FragmentTransactionsCard.class.getName());
                case 1:

                    return FragmentWalletCard.newInstance( new ItemData("Price Statistics", R.drawable.icon_stat_increase, null),R.drawable.icon_stat_increase);
                case 2:

                    return FragmentWalletCard.newInstance(new ItemData("History Chart", R.drawable.icon_chart, null),R.drawable.icon_chart);
                default:
                   return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    private void initComponents() {
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        balanceLabel.setText(String.format("%s BALANCE", currencyCode));
        changeLabel.setText(String.format("%s/XVG", currencyCode));
        balanceAmount.setText(String.format("%s 132.15", currencyCode));
        changeAmount.setText(String.format("%s 0.017", currencyCode));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

