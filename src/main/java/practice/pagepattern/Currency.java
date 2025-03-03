package practice.pagepattern;

public enum Currency {
        EURO("EUR"), US_DOLLAR("USD"), POUND_STERLING("GBP");

        Currency(String href) {
            this.href = href;
        }

        private String href;

        public String getHref() {
            return href;
        }

        public static Currency ofSymbol(String symbol) {
            return switch (symbol) {
                case "$" -> US_DOLLAR;
                case "£" -> POUND_STERLING;
                case "€" -> EURO;
                default -> throw new IllegalArgumentException("Unknown currency: " + symbol);
            };
        }
    }