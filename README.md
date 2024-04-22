# TUUM BACKEND TASK - Andry Avamägi

To start the application clone the project 

```bash
  git clone https://github.com/AndryAvamagi/tuumTask.git
```

Change the directory

```bash
  cd tuumTask/
```

Make sure you have docker running and run this command in the correct directory
```bash
  docker-compose up -d --no-deps --build
```
When the containers have started, open localhost:3000 in your browser

## Explanation of important choices in your solution

At first I needed to think about the structure of the database. With relational databases there is a general rule that we should avoid
duplicate data. I broke this rule.
My first plan was to create a transactions table and an Accounts table. At some point I realized that with each request for a balance sum, 
there will be a SQL request to find all transactions with a certain account ID and currency -> fid the sum of those transactions (In the database 
I am not keeping the direction attribute and I have the transaction amounts as positive or negative numbers). With small scale projects this is fine, but if you have large databases, this request will be very heavy. This is why i chose to add a balance table that updates the sum dynamically with each request. It is an extra request but in the long run it would be benefitial. This creates duplicate data but I think it is acceptable.

This in mind i created an sql script that runs each time my postgres container builds, creating the necessary tables. 

The next step was planning the structure of the project. My first idea was to build a monolithic spring application with all the necessary logic in one place. This seems like a good idea - and it probably is for most smaller projects but with larger projects there will a lot of duplicate unnecessary code when scaling the application horizontally. This is why i chose the microservice'ish route. My plan was to have a central spring app that receives the requests from the client and distributes the jobs to microservices - In my case smaller spring apps with specific jobs. This is why I created a spring app to process account creating and a spring app to process transactions. They all are connected via a message broker (rabbitmq container) and a database (postgres container). 

The main point of this structure is that i can scale the app easily. Everytime there are overwhelming requests for a certain service i could replicate the container that is responsible for the service. For example by default there is 1 container running that is responsible for handling transactions - when the requests increase, I can easily increase the number of transaction containers. All of them are listening to the same queue and the jobs are distributed.


I struggled with the integration tests, as my project was distributed between many containers. 
With integration tests the main purpose is test the workflow of the application, which is why all tests are written in the core/main spring app. I used a great library to implement integration tests - testcontainers. This library creates temporary containers for testing purposes. The setup was quite tricky in my opinion, but I managed to implement working tests for GET endpoints. Incoming requests are handled with MockMvc - testing the workflow throughout the application controller -> service -> repository (mapper in MyBatis case (i think?) -> database -> data request -> response). 
The hard part was implementing rabbitMq and microservice containers in the test. I was faced with errors that i couldnt decrypt. I hope that this is not a deal breaker.

I was quite unhappy with my results and I wanted to make-up for my shortcomings so I wrote a decent front-end for my application. I also implemented a reverse proxy (nginx) between the front-end and the back-end so i could also easily increase the number of cores for this project. Having a reverse proxy is generally good idea to have between the client and the servers as it provides safety and works as a load balancer if needed.


## Estimate on how many transactions can your account application can handle per second on your development machine

In my development machine i have a 13th Gen Intel(R) Core(TM) i5-1335U, 1300 Mhz, 10 Core(s), 12 Logical Processor and 16 GB on ddr4 3200mhz RAM. 

I have 10 cores -  decent parallel processing capabilities.
16 GB of RAM would not be a bottleneck. 
I can utilize my cores to run multiple transaction service containers.
Without any good tests or process analyzing - I would give a rough estimate : my development can handle 300-500ish requests per second. Maybe because the messages are small and the jobs are easy - the best outcome would be around 700 transactions per second. 



## Describe what do you have to consider to be able to scale applications horizontally


Horizontally scaling means increasing the number of machines handling requests. 

To horizonyally scale we need to make sure that the appilcation...

- is Statelessness - Ensure that your application is designed to be stateless, meaning that each request can be processed independently without relying on the state stored locally on the server.

- has a Load Balancer that evenly distributes incoming requests across multiple instances of your application. The easiest example of this is the rabbitmq message broker, that distributes messages for other instances to listen.

- has a shared database between multibple instances. This prevents inconsistency in the data.

- has a service discovery mechanism to dynamically discover and route requests to available instances of your application. This allows new instances to be added or removed without changing it manually.

- has fault tolerance. It is very unlikely that the application will run perfectly 100% of the time. We need to make sure that errors wont break the whole system. This also includes testing your application during varios load conditions. 
