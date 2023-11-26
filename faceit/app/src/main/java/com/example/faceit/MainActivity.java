package com.example.faceit;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ImageView imageView;
    private Button btn;
    private Bitmap bitmap;
    private GraphicOverlay graphicOverlay;
    private Integer width;
    private Integer height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        btn= findViewById(R.id.button);
        btn.setTextColor(Color.BLACK);
        graphicOverlay = findViewById(R.id.graphic_overlay);
        btn.setOnClickListener(view -> FaceDetection());
        Spinner dropdown = findViewById(R.id.spinner);
        String[] values = new String[]{"Image 1","Image 2","Image 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, values);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }
    private void FaceDetection() {
        InputImage image = InputImage.fromBitmap(bitmap, 0);
        FaceDetectorOptions options = new FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .build();
        btn.setEnabled(false);
        FaceDetector detector = FaceDetection.getClient(options);
        Task<List<Face>> listTask = detector.process(image)
                .addOnSuccessListener(
                        new OnSuccessListener<List<Face>>() {
                            @Override
                            public void onSuccess(List<Face> faces) {
                                btn.setEnabled(true);
                                Detect(faces);
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                btn.setEnabled(true);
                                e.printStackTrace();
                            }
                        });

    }

    private void Detect(List<Face> faces) {
        if (faces.size() == 0) {
            showToast();
            return;
        }
        graphicOverlay.clear();
        for (int i = 0; i < faces.size(); i++) {
            Face face = faces.get(i);
            FaceBox faceBox = new FaceBox(graphicOverlay);
            graphicOverlay.add(faceBox);
            faceBox.updateFace(face);
        }
    }

    private void showToast() {
        Toast.makeText(getApplicationContext(), "No face can be detected !!", Toast.LENGTH_SHORT).show();
    }
    private Integer getMaxWidth() {
        if (width == null) {

            width = imageView.getWidth();
        }

        return width;
    }
    private Integer getMaxHeight() {
        if (height == null) {
            height = imageView.getHeight();
        }

        return height;
    }
    private Pair<Integer, Integer> getDimensions() {
        int maxWidth = getMaxWidth();
        int maxHeight = getMaxHeight();

        return new Pair<>(maxWidth, maxHeight);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        graphicOverlay.clear();
        switch (position) {
            case 0:
                bitmap = getBitmapFromAsset(this, "image1.jpeg");
                break;
            case 1:
                bitmap= getBitmapFromAsset(this, "Image2.jpg");
                break;
            case 2:
                bitmap = getBitmapFromAsset(this, "Image3.jpg");
                break;
        }
        if (bitmap  != null) {
            Pair<Integer, Integer> targetedSize = getDimensions();
            int wd = targetedSize.first;
            int ht= targetedSize.second;
            float scaleFactor =
                    Math.max(
                            (float) bitmap.getWidth() / (float) wd,
                            (float) bitmap.getHeight() / (float) ht);

            Bitmap resizedBitmap =
                    Bitmap.createScaledBitmap(
                            bitmap,
                            (int) (bitmap.getWidth() / scaleFactor),
                            (int) (bitmap.getHeight() / scaleFactor),
                            true);

            imageView.setImageBitmap(resizedBitmap);
            bitmap = resizedBitmap;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        AssetManager assetManager = context.getAssets();

        InputStream inputStream;
        Bitmap image = null;
        try {
            inputStream = assetManager.open(filePath);
            image = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}