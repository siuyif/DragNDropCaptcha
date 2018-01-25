package com.siuyifypcaptcha.dragndropcaptcha;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageButton unicorn, planet, r2d2, popeye;
    private ImageView hexagon;
    private String randomText;
    private TextView rndword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // randomise textview
        String [] word = {"UNICORN", "R2D2", "POPEYE", "PLANET"};
        Random rnd = new Random();
        rndword = (TextView) findViewById(R.id.textView);
        randomText = word[rnd.nextInt(word.length)];
        rndword.setText(Html.fromHtml("Verify your existence! Drag the <b><font color=#ff0000>" + randomText + " </b>to the blue hexagon."));

        //views to drag
        unicorn = (ImageButton) findViewById(R.id.imageButton1);
        planet = (ImageButton) findViewById(R.id.imageButton2);
        r2d2 = (ImageButton) findViewById(R.id.imageButton3);
        popeye = (ImageButton)findViewById(R.id.imageButton4);

        //views to drop onto
        hexagon = (ImageView)findViewById(R.id.imageView);

        //set touch listeners
        unicorn.setOnTouchListener(new ChoiceTouchListener());
        planet.setOnTouchListener(new ChoiceTouchListener());
        r2d2.setOnTouchListener(new ChoiceTouchListener());
        popeye.setOnTouchListener(new ChoiceTouchListener());

        //set drag listeners
        hexagon.setOnDragListener(new ChoiceDragListener());
    }

    //To handle touch events for the draggable views
    private final class ChoiceTouchListener implements OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                //To pass data.
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    //To handle and check dragged view was dropped off at the set target.
    private class ChoiceDragListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:

                    //handle the dragged view being dropped over a drop view
                    View view = (View) event.getLocalState();

                    //view dragged item is being dropped on
                    ImageView dropTarget = (ImageView) v;

                    //view being dragged and dropped
                    ImageButton dropped = (ImageButton) view;

                    //checking the randomText as well as the dragged view, to make sure user dragged the right image to the set target
                    if (randomText.toLowerCase().equals("unicorn") && (dropped.getId() == unicorn.getId())) {
                        //stop displaying the view where it was before it was dragged
                        view.setVisibility(View.INVISIBLE);

                        // if conditions met, brought to another new activity
                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }

                    else if (randomText.toLowerCase().equals("r2d2") && (dropped.getId() == r2d2.getId())){
                        view.setVisibility(View.INVISIBLE);

                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }

                    else if (randomText.toLowerCase().equals("popeye") && (dropped.getId() == popeye.getId())){
                        view.setVisibility(View.INVISIBLE);

                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }

                    else if (randomText.toLowerCase().equals("planet") && (dropped.getId() == planet.getId())){
                        view.setVisibility(View.INVISIBLE);

                        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }
                    else {
                        //if didn't match any of the conditions above, user has to restart all over again
                        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                        startActivity(intent);
                        break;
                    }

                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }
}
