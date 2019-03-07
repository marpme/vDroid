package vergecurrency.vergewallet.view.ui.fragment.walletpageviews;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.GsonBuilder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.service.model.Transaction;
import vergecurrency.vergewallet.view.adapter.TransactionsAdapter;

public class FragmentTransactionsPageView extends Fragment {

    double balance = 10d;
    ListView transactionList;

    public FragmentTransactionsPageView() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	
		View rootView;
        // Inflate the layout for this fragment
		if (balance <= 0)
        rootView = inflater.inflate(R.layout.fragment_notransactions, container, false);
		else {
			rootView = inflater.inflate(R.layout.fragment_pageview_transactions, container, false);
			transactionList = rootView.findViewById(R.id.transactions_listview);
			fillTransactionList(transactionList);
		}
		
		return rootView;
    }
    
    private void fillTransactionList (ListView transactionList) {
		JSONParser parser = new JSONParser();
		try {
			//Get the Json
			InputStream is = this.getContext().getAssets().open("transactions.json");
			InputStreamReader isr = new InputStreamReader(is);
			JSONObject jsonObject= (JSONObject) parser.parse(isr);
			JSONArray transactionsListJSON = (JSONArray) jsonObject.get("transactions");
			
			Transaction[] txsArray;
			ArrayList<Transaction> txs;
			txsArray = new GsonBuilder().create().fromJson(transactionsListJSON.toJSONString(), Transaction[].class) ;
			txs = new ArrayList<>(Arrays.asList(txsArray));
			transactionList.setAdapter(new TransactionsAdapter(this.getContext(), txs));
			
			
		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}
	
	
}
