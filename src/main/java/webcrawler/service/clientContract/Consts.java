package webcrawler.service.clientContract;

public class Consts {

    public enum WebCrawlerTypeDTO {
        BFS(0),
        DFS(1);

        private final int value;

        WebCrawlerTypeDTO(int value) {
            this.value = value;
        }

        public static WebCrawlerTypeDTO convertFromInt(Integer value) {
            if(value == null || value < 0 || value >= values().length) {
                return null;
            }

            return values()[value];
        }
    }
}
