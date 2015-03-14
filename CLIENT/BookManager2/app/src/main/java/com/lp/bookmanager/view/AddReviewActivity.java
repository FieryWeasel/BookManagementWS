package com.lp.bookmanager.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lp.bookmanager.R;
import com.lp.bookmanager.data_container.UserManager;
import com.lp.bookmanager.model.Review;
import com.lp.bookmanager.tools.DataManager;
import com.lp.bookmanager.tools.network.NetworkRequests;

public class AddReviewActivity extends Activity implements NetworkRequests.ReviewCreatedListener {

    private SeekBar mGrade;
    private TextView mGradeValue;
    private TextView mTitle;
    private TextView mReview;
    private String mIsbn;
    private Review mFullReview;
    private ProgressDialog mRingProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        mIsbn = getIntent().getStringExtra("isbn");

        mGrade = (SeekBar)findViewById(R.id.mark_review);
        mGradeValue = (TextView)findViewById(R.id.grade_review);
        mTitle = (TextView)findViewById(R.id.title_review);
        TextView author = (TextView) findViewById(R.id.author_review);
        mReview = (TextView)findViewById(R.id.review_review);

        mGrade.setMax(10);
        mGrade.setProgress(5);
        mGrade.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                mGradeValue.setText(String.valueOf(progress)+ "/10");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });

    author.setText(UserManager.getInstance().getUser().getNickname());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_review) {
            mFullReview = new Review(mIsbn,
                    UserManager.getInstance().getUser().getId(),
                    mGrade.getProgress(),
                    mTitle.getText().toString(),
                    mReview.getText().toString());

            NetworkRequests.createReview(this, mFullReview, AddReviewActivity.this);
            mRingProgressDialog = ProgressDialog.show(this, getString(R.string.wait), getString(R.string.loading), true);
            mRingProgressDialog.setCancelable(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onReviewCreatedSuccessful() {
        setResult(RESULT_OK);
        mRingProgressDialog.dismiss();
        DataManager.getInstance().getmListReviews().add(mFullReview);
        finish();
    }

    @Override
    public void onReviewCreatedFailed() {

    }
}
