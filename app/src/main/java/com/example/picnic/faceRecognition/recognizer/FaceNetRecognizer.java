package com.example.picnic.faceRecognition.recognizer;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.picnic.faceRecognition.models.ModelInfo;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.CompatibilityList;
import org.tensorflow.lite.gpu.GpuDelegate;
import org.tensorflow.lite.support.common.TensorOperator;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;
import org.tensorflow.lite.support.tensorbuffer.TensorBufferFloat;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class FaceNetRecognizer implements FaceRecognizer {

    private final Context context;
    private final ModelInfo modelInfo;
    private final ImageProcessor imageProcessor;
    private Interpreter interpreter;

    public FaceNetRecognizer(Context context, ModelInfo modelInfo) {
        this.context = context;
        this.modelInfo = modelInfo;

        imageProcessor = new ImageProcessor.Builder()
                .add(new ResizeOp(
                        modelInfo.getInputDims(),
                        modelInfo.getInputDims(),
                        ResizeOp.ResizeMethod.BILINEAR))
                .add(new StandardizeOp())
                .build();

        Interpreter.Options options = new Interpreter.Options();

        CompatibilityList compatList = new CompatibilityList();

        // use gpu
        if (compatList.isDelegateSupportedOnThisDevice()) {
            options.addDelegate(new GpuDelegate(compatList.getBestOptionsForThisDevice()));
        }

        options.setUseNNAPI(true);

        // TODO : load file
        // interpreter = new Interpreter();
    }

    @Override
    public Float[] getFaceEmbeddingsSync(Bitmap image) {
        // running the model
        ByteBuffer byteBuffer = imageProcessor.process(TensorImage.fromBitmap(image)).getBuffer();

        ArrayList<Float[]> output = new ArrayList<>();

        Float[] arr = new Float[1];
        arr[0] = (float) modelInfo.getOutputDims();
        output.add(arr);

        interpreter.run(byteBuffer, output);

        return output.get(0);
    }


    static class StandardizeOp implements TensorOperator {

        // newElement = ( element - mean ) / std_deviation
        @Override
        public TensorBuffer apply(TensorBuffer input) {

            float[] pixels = input.getFloatArray();

            // find mean
            float mean = 0;
            for (float pixel : pixels) {
                mean += pixel;
            }
            mean /= pixels.length;

            // find sum of (pixel - mean)^2
            double sum = 0;
            for (float pixel : pixels) {
                sum += Math.pow((pixel - mean), 2);
            }

            float std = (float) (sum / pixels.length);
            std = (float) Math.max(std, 1f / Math.sqrt(pixels.length));

            for (int i = 0; i < pixels.length; i++) {
                pixels[i] = (pixels[i] - mean) / std;
            }

            TensorBuffer outputBuffer = TensorBufferFloat.createFixedSize(input.getShape(), DataType.FLOAT32);
            outputBuffer.loadArray(pixels);

            return outputBuffer;
        }
    }
}
