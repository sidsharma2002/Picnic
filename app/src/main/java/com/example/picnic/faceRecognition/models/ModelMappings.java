package com.example.picnic.faceRecognition.models;

public class ModelMappings {

    public static ModelInfo getFaceNetDefaultInfo() {
        return new ModelInfo("FaceNet",
                "facenet.tflite",
                0.4f,
                10f,
                128,
                160);
    }
}
