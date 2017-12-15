package com.joshpayne1.androidartist;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.joshpayne1.androidartist.CustomView;
import com.joshpayne1.androidartist.EditorGameState;
import com.joshpayne1.androidartist.R;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

public class CanvasActivity extends AppCompatActivity implements ColorPickerDialogListener {

    private com.joshpayne1.androidartist.CustomView cv;
    private EditorGameState ed;
    private String name;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        cv = (CustomView) findViewById(R.id.canvas);
        ed = EditorGameState.getInstance();

    }

    public void clicked(View view) {
        ColorPickerDialog.newBuilder()
                .setDialogType(ColorPickerDialog.TYPE_CUSTOM)
                .setAllowPresets(false)
                .setColor(Color.BLACK)
                .setShowAlphaSlider(true)
                .setShowColorShades(true)
                .show(this);
    }

    public void clear(View view) {
        cv.clearCanvas();
    }

    @Override
    public void onColorSelected(int dialogId, int color) {
        Toast.makeText(CanvasActivity.this, "Selected Color: #" + Integer.toHexString(color), Toast.LENGTH_SHORT).show();
        cv.setPathColor(color);
    }

    public void saveImage(View view) {
        bitmap = cv.getBitmap();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("What's the name of your new image?");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                name = input.getText().toString().toLowerCase();
                if (name.isEmpty()) {
                    Toast.makeText(CanvasActivity.this, "Enter a name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bitmap bmp = null;
                bmp = ed.getBitmapImage(name + ".png");
                if (bmp==null) {
                    bitmapToInternal(bitmap, name);
                    Toast.makeText(CanvasActivity.this, "Image saved!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    dialog.cancel();
                    duplicateImage();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void duplicateImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Duplicate image name found. Overwrite?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bitmapToInternal(bitmap, name);
                Toast.makeText(CanvasActivity.this, "Image saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void bitmapToInternal(Bitmap bmp, String name) {
        bmp = CropBitmap(bmp);
        try {
            FileOutputStream fos = this.ed.getContext().openFileOutput(name.toLowerCase() + ".png.image", Context.MODE_PRIVATE);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            fos.write(byteArray);
            fos.close();
        } catch (Exception e) {
        }
    }

    //https://stackoverflow.com/questions/27753634/android-bitmap-save-without-transparent-area
    public Bitmap CropBitmap(Bitmap bmp)
    {
        int minX = bmp.getWidth();
        int minY = bmp.getHeight();
        int maxX = -1;
        int maxY = -1;
        for(int y = 0; y < bmp.getHeight(); y++)
        {
            for(int x = 0; x < bmp.getWidth(); x++)
            {
                int alpha = (bmp.getPixel(x, y) >> 24) & 255;
                if(alpha > 0)
                {
                    if(x < minX)
                        minX = x;
                    if(x > maxX)
                        maxX = x;
                    if(y < minY)
                        minY = y;
                    if(y > maxY)
                        maxY = y;
                }
            }
        }
        if((maxX < minX) || (maxY < minY))
            return null;
        return Bitmap.createBitmap(bmp, minX, minY, (maxX - minX) + 1, (maxY - minY) + 1);
    }

    public void home(View view) {
        finish();
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
