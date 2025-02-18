package com.document;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;
import java.util.UUID;

public class GeneratePresignedUrl {
    public static void main(String[] args) {
        // Set up AWS credentials (use environment variables in production)
        String accessKey = System.getenv("AWS_ACCESS_KEY"); // Use environment variables
        String secretKey = System.getenv("AWS_SECRET_KEY"); // Use environment variables
        String region = "us-east-1";
        String bucketName = "document-uploads-bucket-poc";

        // Example borrower and loan details
        String borrowerId = "32756";
        String loanId = "12345";
        String fileName = "mortgage_agreement.pdf"; // Replace with actual file name
        String fileExtension = fileName.substring(fileName.lastIndexOf('.'));

        // Generate a unique object key using borrowerId, loanId, and fileExtension
        String uniqueObjectKey = "borrower_" + borrowerId + "/loan_" + loanId + "/" + UUID.randomUUID().toString() + fileExtension;

        // Create S3Presigner
        S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();

        // Generate presigned URL
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(uniqueObjectKey)
                .contentType("application/pdf")
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(5)) // 5 minutes
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
        URL url = presignedRequest.url();

        // Output the URL for the user to use
        System.out.println("Pre-Signed URL: " + url.toString());
    }
}