package service.domain;

public class Consts {

    public enum WebCrawlerType {
        BFS(0),
        DFS(1);

        private final int value;

        WebCrawlerType(int value) {
            this.value = value;
        }

        public static WebCrawlerType convertFromInt(Integer value) {
            if(value == null || value < 0 || value >= values().length) {
                return null;
            }

            return values()[value];
        }
    }
}
