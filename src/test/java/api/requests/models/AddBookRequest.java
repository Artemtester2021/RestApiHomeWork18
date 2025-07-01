package api.requests.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
public class AddBookRequest {

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("collectionOfIsbns")
    private List<Isbn> collectionOfIsbns;
    public AddBookRequest(String userId, List<Isbn> collectionOfIsbns) {
        this.userId = userId;
        this.collectionOfIsbns = collectionOfIsbns;
    }
    public static class Isbn {
        @JsonProperty("isbn")
        private String isbn;
        public Isbn(String isbn) {
            this.isbn = isbn;
        }
    }
}
