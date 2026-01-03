import java.io.*;
import java.util.*;

class Hotel {
    private String city;
    private String name;
    private int stars;

    public Hotel(String city, String name, int stars) {
        this.city = city;
        this.name = name;
        this.stars = stars;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    @Override
    public String toString() {
        return String.format("Город: %-15s Отель: %-20s Звёзды: %d", city, name, stars);
    }
}

public class Main {
    private List<Hotel> hotels;

    public Main() {
        hotels = new ArrayList<>();
    }

    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            String currentCity = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (currentCity == null) {
                    currentCity = line;
                } else {
                    String[] parts = line.split("\\s+");

                    if (parts.length >= 2) {
                        String hotelName = String.join(" ",
                                Arrays.copyOfRange(parts, 0, parts.length - 1));

                        int stars;
                        try {
                            stars = Integer.parseInt(parts[parts.length - 1]);
                        } catch (NumberFormatException e) {
                            currentCity = null;
                            continue;
                        }

                        hotels.add(new Hotel(currentCity, hotelName, stars));
                    }
                    currentCity = null;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }


    public void displaySortedHotels() {
        List<Hotel> sortedHotels = new ArrayList<>(hotels);

        Collections.sort(sortedHotels, new Comparator<Hotel>() {
            @Override
            public int compare(Hotel h1, Hotel h2) {
                int cityCompare = h1.getCity().compareTo(h2.getCity());
                if (cityCompare != 0) {
                    return cityCompare;
                }
                return Integer.compare(h2.getStars(), h1.getStars());
            }
        });

        System.out.println("<<< Отели отсортированные по городам и звездам >>>");
        for (Hotel hotel : sortedHotels) {
            System.out.println(hotel);
        }
        System.out.println();
    }

    public void findHotelsByCity(String city) {
        System.out.println("<<< Отели в городе " + city + " >>>");

        hotels.stream()
                .filter(hotel -> hotel.getCity().equalsIgnoreCase(city))
                .sorted((h1, h2) -> Integer.compare(h2.getStars(), h1.getStars()))
                .forEach(System.out::println);

        System.out.println();
    }

    public void findCitiesByHotelName(String hotelName) {
        System.out.println("<<< Города с отелем '" + hotelName + "' >>>");

        Set<String> cities = new TreeSet<>();

        hotels.stream()
                .filter(hotel -> hotel.getName().equalsIgnoreCase(hotelName))
                .forEach(hotel -> cities.add(hotel.getCity()));

        if (cities.isEmpty()) {
            System.out.println("Отели с названием '" + hotelName + "' не найдены.");
        } else {
            cities.forEach(System.out::println);
        }
        System.out.println();
    }

    public void demonstrateCollections() {

        Map<String, List<Hotel>> hotelsByCity = new HashMap<>();
        for (Hotel hotel : hotels) {
            hotelsByCity.computeIfAbsent(hotel.getCity(), k -> new ArrayList<>()).add(hotel);
        }

        System.out.println("Отели сгруппированные по городам:");
        for (Map.Entry<String, List<Hotel>> entry : hotelsByCity.entrySet()) {
            System.out.println("Город: " + entry.getKey() + ", количество отелей: " + entry.getValue().size());
        }
        System.out.println();

        Set<String> uniqueCities = new TreeSet<>();
        for (Hotel hotel : hotels) {
            uniqueCities.add(hotel.getCity());
        }
        System.out.println("Уникальные города (отсортированные): " + uniqueCities);
        System.out.println();
    }

    public static void main(String[] args) {
        Main manager = new Main();

        manager.readFromFile("hotel.txt");

        manager.demonstrateCollections();
        manager.displaySortedHotels();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите название города для поиска отелей: ");
        String city = scanner.nextLine();
        manager.findHotelsByCity(city);

        System.out.print("Введите название отеля для поиска городов: ");
        String hotelName = scanner.nextLine();
        manager.findCitiesByHotelName(hotelName);

        scanner.close();
    }
}
