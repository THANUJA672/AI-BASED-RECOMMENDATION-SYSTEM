import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommendationSystem {

    public static void main(String[] args) {
        try {
            // Load the dataset (ratings.csv) into a DataModel
            DataModel model = new FileDataModel(new File("ratings.csv"));
            
            // Create a similarity measure (Tanimoto Coefficient)
            TanimotoCoefficientSimilarity similarity = new TanimotoCoefficientSimilarity(model);
            
            // Create a neighborhood of users (we'll use 2 nearest neighbors here)
            NearestNUserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
            
            // Create a recommender system
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
            
            // Get recommendations for user 1
            List<RecommendedItem> recommendations = recommender.recommend(1, 2);  // Recommend 2 items for user 1
            
            // Display recommendations
            System.out.println("Recommendations for User 1:");
            for (RecommendedItem item : recommendations) {
                System.out.println("Item: " + item.getItemID() + " - Estimated rating: " + item.getValue());
            }
        } catch (IOException | TasteException e) {
            e.printStackTrace();
        }
    }
}
