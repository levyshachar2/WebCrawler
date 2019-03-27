package webcrawler.service.domain;

public interface Consts {


    interface ConfigurationKeys {

        String REGULAR_EXPRESSION = "REGULAR_EXPRESSION";

    }

    enum WebCrawlerType {
        BFS(0),
        DFS(1);

        private final int value;

        WebCrawlerType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }


        public static WebCrawlerType convertFromInt(Integer value) {
            if(value == null || value < 0 || value >= values().length) {
                return null;
            }

            return values()[value];
        }
    }
}
