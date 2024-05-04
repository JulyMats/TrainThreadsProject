import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RouteElements.startSimulation();

        System.out.println("\n\n\nHello! Welcome to my project");
        while(true) {
            System.out.println("\n\n========================Please choose an option and enter number=================================" +
                    "\n1 -> Make a new station" +
                    "\n2 -> Remove the station" +
                    "\n3 -> Remove car from the train" +
                    "\n4 -> Add new car to the train" +
                    "\n5 -> Make a new locomotive" +
                    "\n6 -> Remove the locomotive" +
                    "\n7 -> Print information" +
                    "\n8 -> Print a list with all stations" +
                    "\n9 -> Print a list with all locomotives");
            if(scanner.hasNextInt()) {
                int option = scanner.nextInt();
                switch (option) {
                    case 1: {
                        System.out.println("Please enter name of the station you want to create: ");
                        String name = scanner.next();
                        RouteElements.createStation(name);
                        System.out.println("Station " + name + " was successfully created! If you want to check it you can print a list with all stations");
                        break;
                    }
                    case 2: {
                        System.out.println("Please enter ID number of the station you want to remove. " +
                                "\nTo make your choice easier I have a list with all stations");
                        RouteElements.printStations();
                        System.out.println("Choose ID number of the station");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int name = scanner.nextInt();
                        RouteElements.removeStation(name);
                        System.out.println("Station " + name + " was successfully removed! If you want to check it you can print a list with all stations");
                        break;
                    }
                    case 3: {
                        System.out.println("Please enter ID of the locomotive in which you want to remove a car");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int id = scanner.nextInt();
                        System.out.println("Now you need to decide which car you would like to remove. " +
                                "\nTo make your choice easier I have a list with all cars");
                        RouteElements.printCars();
                        System.out.println("Please enter ID of the car you want to remove: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int idCar = scanner.nextInt();
                        Car car = RouteElements.getCars().get(idCar);
                        try {
                            RouteElements.getLocomotives().get(id).deleteCar(car);
                        } catch (Exception e) {
                            System.out.println("Oooops! You out of bounds");
                            e.printStackTrace();
                        }

                        System.out.println("Car " + car.getIdNumber() + " was successfully removed from the Train with ID = "  + id + " ! If you want to check it you can print a list with all cars");
                        break;
                    }
                    case 4: {
                        System.out.println("Please enter ID of the locomotive to which you want to add a car");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int id = scanner.nextInt();
                        System.out.println("Now you need to decide which car you would like to add. " +
                                "\nTo make your choice easier I have a list with all cars");
                        RouteElements.printCars();
                        System.out.println("Please enter Id number: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int idCar = scanner.nextInt();
                        Car car = RouteElements.getCars().get(idCar);
                        try {
                            RouteElements.getLocomotives().get(id).addCar(car);
                        } catch (CargoWeightException e) {
                            e.printStackTrace();
                        } catch (CarsWithElConnectionException e) {
                            e.printStackTrace();
                        } catch (CarOutOfBoundsExeption e) {
                            e.printStackTrace();
                        }
                        System.out.println("Car " + car.getIdNumber() + " was successfully added to the Train with ID = "  + id + " ! If you want to check it you can print a list with all cars");
                        break;
                    }
                    case 5: {
                        System.out.println("Please enter name of the locomotive you want to create");
                        String name = scanner.next();
                        System.out.println("Now you need to decide where train will start and end route. " +
                                "\nTo make your choice easier I have a list with all stations");
                        RouteElements.printStations();
                        System.out.println("Please enter ID number of the station. \nHome station: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int homeStation = scanner.nextInt();
                        System.out.println("Start station: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int startStation = scanner.nextInt();
                        System.out.println("End station: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int endStation = scanner.nextInt();
                        System.out.println("Max number of cars: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int maxNumberOfCars = scanner.nextInt();
                        System.out.println("Max number of cars which required electrical connection: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int maxNumOfCarsWithElConnect = scanner.nextInt();
                        System.out.println("Max cargo weight: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        double maxCargoWeight = scanner.nextDouble();
                        System.out.println("And it's the last one. \nPlease enter start speed of the train: ");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        double speed = scanner.nextDouble();
                        RouteElements.createLocomotive(name, homeStation, startStation, endStation, maxNumberOfCars, maxCargoWeight, maxNumOfCarsWithElConnect, speed);
                        System.out.println("Locomotive " + name + " was successfully created and started his journey! If you want to check it you can print a list with all locomotives");
                        break;
                    }
                    case 6: {
                        System.out.println("Please enter name of the locomotive you want to remove. " +
                                "\nTo make your choice easier I have a list with all locomotives");
                        RouteElements.printLocomotives();
                        System.out.println("Enter name of the locomotive you want to remove ");
                        String name = scanner.next();
                        RouteElements.removeLocomotive(name);
                        System.out.println("Locomotive " + name + " was successfully removed! If you want to check it you can print a list with all locomotives");
                        break;
                    }
                    case 7: {
                        System.out.println("You need to choose the train. Please give me ID Number of the train");
                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int id = scanner.nextInt();

                        System.out.println("================You can choose which piece of information you would like to get================== " +
                                "\n1 ---> simple information about the train" +
                                "\n2 ---> percentage of completed journey between start and destination" +
                                "\n3 ---> information about the cars, the number of persons transported and the goods transported" +
                                "\n4 ---> percentage of the completed journey between the nearest railway stations on your route.");

                        while(!scanner.hasNextInt()) {
                            scanner.nextLine();
                            System.out.println("I'm sorry, but you should write a number. Please try again");
                        }
                        int choice = scanner.nextInt();
                        RouteElements.getLocomotives().sort((l1, l2) -> {
                            return Integer.compare(l1.getIdNumber(), l2.getIdNumber());
                        });

                        switch (choice) {
                            case 1: {
                                System.out.println("The information about the train " +
                                        "\n" + RouteElements.getLocomotives().get(id).toString());
                                break;
                            }
                            case 2: {
                                System.out.println("The percentage of completed journey between start and destination stations - " +
                                        RouteElements.getLocomotives().get(id).completedRouteBetweenStartEnd() + "%");
                                break;
                            }
                            case 3: {
                                RouteElements.printCars();
                                break;
                            }
                            case 4: {
                                System.out.println("The percentage of completed journey between the nearest railway stations on your route - " +
                                        RouteElements.getLocomotives().get(id).completedRouteBetweenTwoStations() + "%");
                                break;
                            }
                            default: {
                                System.out.println("Please choose a number between 1 - 4");
                            }
                        }
                        break;
                    }
                    case 8: {
                        System.out.println("A list of all stations: ");
                        RouteElements.printStations();
                        break;
                    }
                    case 9: {
                        RouteElements.getLocomotives().sort((l1, l2) -> {
                            return Integer.compare(l1.getIdNumber(), l2.getIdNumber());
                        });
                        System.out.println("A list of all locomotives: ");
                        RouteElements.printLocomotives();
                        break;
                    }
                    default: {
                        System.out.println("Please choose a number between 1 - 9");
                    }
                }
            } else {
                System.out.println("I'm sorry, but you should write a number");
                scanner.next();
            }
        }
    }
}
