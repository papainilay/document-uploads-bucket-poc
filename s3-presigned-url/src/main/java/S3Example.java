import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

public class S3Example {
    public static void main(String[] args) {
        // Create S3 client
        S3Client s3 = S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        // List buckets
        ListBucketsResponse response = s3.listBuckets();
        for (Bucket bucket : response.buckets()) {
            System.out.println("Bucket: " + bucket.name());
        }
    }
}