package io.github.kunalkene1797.scarnedice;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    Button rstbtn,rollbtn,holdbtn;
    TextView yourolled, pscore, compscoreview;
    int compscore = 0;
    int currentscore = 0;
    int cscore = 0;
    int playerscore = 0;
    static final int winscore = 100;
    int initialscore,cpuinitialscore;
    ImageView dicefaceview;
    public static int diceface[] = {
            R.drawable.alea_1,
            R.drawable.alea_2,
            R.drawable.alea_3,
            R.drawable.alea_4,
            R.drawable.alea_5,
            R.drawable.alea_6
    };
    public Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rollbtn =(Button)findViewById(R.id.rollbtn);
        holdbtn =(Button)findViewById(R.id.holdbtn);
        rstbtn = (Button)findViewById(R.id.rstbtn);
        dicefaceview = (ImageView)findViewById(R.id.dicefaceview);
        yourolled = (TextView)findViewById(R.id.yourolled);
        pscore = (TextView)findViewById(R.id.pscore);
        compscoreview = (TextView)findViewById(R.id.compscoreview);

        yourolled.setText("Press Roll To Throw The Dice");

        rollbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rolldice();
                cpuinitialscore = compscore;
            }
        });

        rstbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                resetscore();
            }
        });

        holdbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                computerplay();
                initialscore = playerscore;
            }
        });

        holdbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                hold();
            }
        });


    }

    public void rolldice() {
    int dicevalue = random.nextInt(6)+1;
        currentscore =0;
        if (dicevalue==1){
            dicefaceview.setImageResource(diceface[dicevalue-1]);
            yourolled.setText("Your Rolled "+ dicevalue+", Score this Turn = 0");
            holdbtn.setEnabled(false);
            rollbtn.setEnabled(false);
            playerscore = initialscore;
            setscore();
            computerplay();
        }
        else{
            dicefaceview.setImageResource(diceface[dicevalue-1]);
            yourolled.setText("Your Rolled "+ dicevalue);
            currentscore += dicevalue;
            playerscore += currentscore;
            if(playerscore>=winscore){
                Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
                setscore();
                pscore.setTextColor(getResources().getColor(R.color.green));
                AlertDialog.Builder altdlg = new AlertDialog.Builder(this);
                altdlg.setTitle("You Win!");
                altdlg.setCancelable(true);
                altdlg.setPositiveButton("Awesome!", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetscore();
                    }
                });
                altdlg.create().show();
            }else {
                setscore();
            }
        }
    }
    public void setscore(){
        pscore.setText("Your Score: "+playerscore);
        compscoreview.setText("Computer Score: "+compscore);
    }

    public void hold(){
        holdbtn.setEnabled(false);
        rollbtn.setEnabled(false);
        initialscore = playerscore;
        cpuinitialscore = compscore;
        computerplay();
    }

    private void computerplay() {
        holdbtn.setEnabled(false);
        rollbtn.setEnabled(false);
        int dicevalue;
        cscore = 0;
        do{
            dicevalue = random.nextInt(6) + 1;
            if (dicevalue == 1) {
                dicefaceview.setImageResource(diceface[dicevalue - 1]);
                holdbtn.setEnabled(true);
                rollbtn.setEnabled(true);
                compscore = cpuinitialscore;
                Toast.makeText(this, "Computer Rolled " + dicevalue, Toast.LENGTH_SHORT).show();
                setscore();
                break;

            } else {
                dicefaceview.setImageResource(diceface[dicevalue - 1]);
                cscore += dicevalue;
                setscore();
                Toast.makeText(this, "Computer Rolled " + dicevalue, Toast.LENGTH_SHORT).show();
                compscore += dicevalue;
            }
        }while (cscore < 10);

        if(compscore>=winscore){
            Toast.makeText(this, "Computer Wins!", Toast.LENGTH_SHORT).show();
            compscoreview.setTextColor(getResources().getColor(R.color.green));
            setscore();
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setTitle("Computer Wins!");
            dlgAlert.setCancelable(true);
            dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            resetscore();
                        }
                    });
            dlgAlert.create().show();
        }else {
            setscore();
        }


        holdbtn.setEnabled(true);
        rollbtn.setEnabled(true);
    }



    private void resetscore() {
        playerscore = 0;
        currentscore = 0;
        compscore = 0;
        cscore = 0;
        holdbtn.setEnabled(true);
        rollbtn.setEnabled(true);
        yourolled.setText("Press Roll To Throw The Dice");
        compscoreview.setText("Computer Score: 0");
        pscore.setText("Your Score: 0");
    }



}
