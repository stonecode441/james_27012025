/**
 * Created By Chivo
 */


public class main {
    public static void main(String[] args) {
        // Disable SSL verification
        SSLUtils.disableSslVerification();

        // Test API endpoints
        System.out.println("Creating an employer:");
        PostRequestExample postExample = new PostRequestExample();
        postExample.createEmployer();

        System.out.println("\nFetching all employees:");
        GetAllEmployeesExample getAllExample = new GetAllEmployeesExample();
        getAllExample.fetchAllEmployees();

        System.out.println("\nFetching one employee:");
        GetOneEmployeeExample getOneExample = new GetOneEmployeeExample();
        getOneExample.fetchOneEmployee();
    }
}
