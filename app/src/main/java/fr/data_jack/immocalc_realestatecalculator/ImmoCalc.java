package fr.data_jack.immocalc_realestatecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ImmoCalc extends AppCompatActivity {
// TODO - 1/ set orientation change ?
// TODO - 2/ add activity ?

    private static int calculateMode = 1;
    private Button changeToCapitaly = null;
    private Button changeToMonthly = null;
    private Button calculateNow = null;

    private Animation slideUp = null;
    private Animation slideDown = null;

    private TextView subtitle = null;

    private TextView textviewCapital = null;
    private EditText editextCapital = null;
    private TextView textviewDuration = null;
    private EditText editextDuration = null;
    private TextView textviewTeg = null;
    private EditText editextTeg = null;
    private CheckBox checkboxAdvancedOptions = null;
    private RadioGroup radioGroupMethod = null;
    private TextView textviewAssu = null;
    private EditText editextAssu = null;

    private TextView textviewResultText = null;
    private TextView textviewResultCalcul = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immo_calc);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // button capitaly
        changeToCapitaly = (Button) findViewById(R.id.change_to_capitaly);
        changeToCapitaly.setOnClickListener(changeCapitalyOnClickListener);
        // button monthly
        changeToMonthly = (Button) findViewById(R.id.change_to_monthly);
        changeToMonthly.setOnClickListener(changeMonthlyOnClickListener);
        // button calculate
        calculateNow = (Button) findViewById(R.id.button_calculate);
        calculateNow.setOnClickListener(calculateOnClickListener);

        // checkbox button
        checkboxAdvancedOptions = (CheckBox) findViewById(R.id.content_checkbox_advanced);
        checkboxAdvancedOptions.setOnClickListener(checkAdvancedOptionOnClickListener);
        // TODO - on checked checkboxAdvancedOptions.setOnClickListener(calculateOnClickListener);
        // radio buttons
        radioGroupMethod = (RadioGroup) findViewById(R.id.content_radiogroup_method);
        // TODO - on change radioGroupMethod.setOnClickListener(calculateOnClickListener);

        // animation
        slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        radioGroupMethod.startAnimation(slideUp);

        // elements instanciation
        subtitle = (TextView) findViewById(R.id.subtitle);
        textviewCapital = (TextView) findViewById(R.id.textview_capital);
        editextCapital = (EditText) findViewById(R.id.editext_capital);
        textviewDuration = (TextView) findViewById(R.id.textview_duration);
        editextDuration = (EditText) findViewById(R.id.editext_duration);
        textviewTeg = (TextView) findViewById(R.id.textview_teg);
        editextTeg = (EditText) findViewById(R.id.editext_teg);
        textviewAssu = (TextView) findViewById(R.id.textview_assu);
        editextAssu = (EditText) findViewById(R.id.editext_assu);

        textviewResultText = (TextView) findViewById(R.id.textview_result_text);
        textviewResultCalcul = (TextView) findViewById(R.id.textview_result_calcul);

        // text change listener
    }

    private View.OnClickListener changeCapitalyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ImmoCalc.calculateMode == 0) {
                updateCalculateMode();
            }
        }
    };

    private View.OnClickListener changeMonthlyOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (ImmoCalc.calculateMode == 1) {
                updateCalculateMode();
            }
        }
    };

    private View.OnClickListener checkAdvancedOptionOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (checkboxAdvancedOptions.isChecked()) {
                radioGroupMethod.startAnimation(slideDown);
            } else {
                radioGroupMethod.startAnimation(slideUp);
            }
        }
    };

//    private View.OnTouchListener calculateOnTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event);
//
//        }
//    };

    private View.OnClickListener calculateOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            float monthlyOrCapitalFloat = 0.0f;
            float durationFloat = 0.0f;
            double tegDouble = 0.0d;
            double assuDouble = 0.0d; // TODO - prise en compte de l'assurance
            Double result = 0.0d;

            String monthlyOrCapital = editextCapital.getText().toString();
            String duration = editextDuration.getText().toString();
            String teg = editextTeg.getText().toString();
            String assu = editextAssu.getText().toString();
            String errorInput = getResources().getString(R.string.error_input);
            String currencySymbol = getResources().getString(R.string.currency_symbol);

            if (monthlyOrCapital.equals("") || monthlyOrCapital.equals(".") || monthlyOrCapital.matches(".*\\..*\\..*")) {
                Toast.makeText(ImmoCalc.this, textviewCapital.getText().toString() + errorInput + monthlyOrCapital, Toast.LENGTH_SHORT).show();
                return;
            }
            monthlyOrCapitalFloat = Float.parseFloat(monthlyOrCapital);
            if (duration.equals("") || duration.equals(".") || duration.matches(".*\\..*\\..*")) {
                Toast.makeText(ImmoCalc.this, textviewDuration.getText().toString() + errorInput + R.string.error_input + duration, Toast.LENGTH_SHORT).show();
                return;
            }
            durationFloat = Float.parseFloat(duration);
            if (teg.equals("") || teg.equals(".") || teg.matches(".*\\..*\\..*")) {
                Toast.makeText(ImmoCalc.this, textviewTeg.getText().toString() + errorInput + R.string.error_input + teg, Toast.LENGTH_SHORT).show();
                return;
            }
            tegDouble = Double.parseDouble(teg);
            if (assu.equals("") || assu.equals(".") || assu.matches(".*\\..*\\..*")) {
                Toast.makeText(ImmoCalc.this, textviewAssu.getText().toString() + errorInput  + R.string.error_input + assu, Toast.LENGTH_SHORT).show();
                return;
            }
            // assuDouble = Double.parseDouble(assu); // TODO - assurance

            if (ImmoCalc.calculateMode == 0) {
                result = getTotalCapital(monthlyOrCapitalFloat, tegDouble, durationFloat);
                textviewResultCalcul.setText(result.toString() + currencySymbol);
            } else {
                result = getMonthlyTEG(monthlyOrCapitalFloat, tegDouble, durationFloat);
                textviewResultCalcul.setText(result.toString() + currencySymbol);
            }
        }
    };

    private void updateCalculateMode()
    {
        ImmoCalc.calculateMode++;
        if (ImmoCalc.calculateMode > 1) {
            ImmoCalc.calculateMode = 0;
        }

        switch(ImmoCalc.calculateMode) {
            case 1 :
                changeToCapitaly.setBackgroundResource(R.color.backgroundPrimary);
                changeToCapitaly.setTextColor(getResources().getColor(R.color.textPrimary));
                changeToMonthly.setBackgroundResource(R.color.backgroundSecondary);
                changeToMonthly.setTextColor(getResources().getColor(R.color.textSecondary));

                subtitle.setText(R.string.app_name_mode_monthly);
                textviewCapital.setText(R.string.textview_capital);
                textviewResultText.setText(R.string.textview_result_text);
            break;
            case 0 :
            default:
                changeToCapitaly.setBackgroundResource(R.color.backgroundSecondary);
                changeToCapitaly.setTextColor(getResources().getColor(R.color.textSecondary));
                changeToMonthly.setBackgroundResource(R.color.backgroundPrimary);
                changeToMonthly.setTextColor(getResources().getColor(R.color.textPrimary));

                subtitle.setText(R.string.app_name_mode_capitaly);
                textviewCapital.setText(R.string.textview_result_text);
                textviewResultText.setText(R.string.textview_capital);
            break;
        }
    }

    public double getMonthlyTEG(float capital, double taux, float year)
    {
		double result = 0.0d;
        double methodRate = 0.0d;
        if (checkboxAdvancedOptions.isChecked() && radioGroupMethod.getCheckedRadioButtonId() == R.id.radio_button_method_1) {
            methodRate = this.getProportionalRate(taux);
        } else {
            methodRate = this.getActurielleRate(taux);
        }
        float months = 12 * year;
		result = round((double) (capital * methodRate * Math.pow((1.0d + methodRate), months)) / (Math.pow((1.0d + methodRate), months) - 1.0d), 2);

		return result;
    }

    public double getTotalCapital(float monthly, double taux, float year)
    {
        double result = 0.0d;
        double methodRate = 0.0d;
        if (checkboxAdvancedOptions.isChecked() && radioGroupMethod.getCheckedRadioButtonId() == R.id.radio_button_method_1) {
            methodRate = this.getProportionalRate(taux);
        } else {
            methodRate = this.getActurielleRate(taux);
        }
		float months = 12 * year;
		result = round((double) ( monthly * ((Math.pow((1.0d + methodRate), months) - 1.0d) / (methodRate * Math.pow((1.0d + methodRate), months))) ), 2);
		
		return result;
    }

    /**
     * Return acturielle rate (based on PEL yearly rate)
     *
     * @param rate
     * @return
     */
    private double getActurielleRate(double rate)
    {
        double percentTaux = rate/100.d;
        double acturielleRate = Math.pow((1.0d + percentTaux), 1.0d/12.0d) - 1.0d;

        return acturielleRate;
    }

    /**
     * Return proportional rate
     *
     * @param rate
     * @return
     */
    private double getProportionalRate(double rate)
    {
        double percentTaux = rate/100.d;
        double proportionalRate = percentTaux/12.0d;

        return proportionalRate;
    }
	
	/**
     * If B > 3, cast float
     * Else cast int ...
     *
     * @param A
     * @param B
     * @return
     */
	public static double round(double A, int B)
	{
        // TODO - check method integrity
        if (B <= 3) {
            return (double) ( (int) (A * Math.pow(10, B) + .5)) / Math.pow(10, B);
        } else {
            return (double) ( (float) (A * Math.pow(10, B) + .5)) / Math.pow(10, B);
        }
    }
}
