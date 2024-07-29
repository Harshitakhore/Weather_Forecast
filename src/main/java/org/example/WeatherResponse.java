package org.example;

import java.util.List;

public class WeatherResponse {
    private Location location;
    private Forecast forecast;


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Forecast getForecast() {
        return forecast;
    }

    public void setForecast(Forecast forecast) {
        this.forecast = forecast;
    }

    public static class Location {
        private String code;
        private String name;
        private String timezone;
        private Coordinates coordinates;


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
        }

        public static class Coordinates {
            private double latitude;
            private double longitude;


            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }
    }

    public static class Forecast {
        private List<Item> items;


        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public static class Item {
            private String date;
            private Weather weather;
            private Temperature temperature;
            private Wind wind;
            private Astronomy astronomy;


            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public Weather getWeather() {
                return weather;
            }

            public void setWeather(Weather weather) {
                this.weather = weather;
            }

            public Temperature getTemperature() {
                return temperature;
            }

            public void setTemperature(Temperature temperature) {
                this.temperature = temperature;
            }

            public Wind getWind() {
                return wind;
            }

            public void setWind(Wind wind) {
                this.wind = wind;
            }

            public Astronomy getAstronomy() {
                return astronomy;
            }

            public void setAstronomy(Astronomy astronomy) {
                this.astronomy = astronomy;
            }

            public static class Weather {
                private int state;
                private String text;
                private String icon;

                // Getters and setters
                public int getState() {
                    return state;
                }

                public void setState(int state) {
                    this.state = state;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }
            }

            public static class Temperature {
                private int min;
                private int max;

                // Getters and setters
                public int getMin() {
                    return min;
                }

                public void setMin(int min) {
                    this.min = min;
                }

                public int getMax() {
                    return max;
                }

                public void setMax(int max) {
                    this.max = max;
                }
            }

            public static class Wind {
                private String unit;
                private String direction;
                private int min;
                private int max;

                // Getters and setters
                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public String getDirection() {
                    return direction;
                }

                public void setDirection(String direction) {
                    this.direction = direction;
                }

                public int getMin() {
                    return min;
                }

                public void setMin(int min) {
                    this.min = min;
                }

                public int getMax() {
                    return max;
                }

                public void setMax(int max) {
                    this.max = max;
                }
            }

            public static class Astronomy {
                private String sunrise;
                private String sunset;

                // Getters and setters
                public String getSunrise() {
                    return sunrise;
                }

                public void setSunrise(String sunrise) {
                    this.sunrise = sunrise;
                }

                public String getSunset() {
                    return sunset;
                }

                public void setSunset(String sunset) {
                    this.sunset = sunset;
                }
            }
        }
    }
}

