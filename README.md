# BlockToon

BlockToon is a platform for Webtoon(an online comics) service based on Ethereum blockchain, and the InterPlanetary File System (IPFS).  
</br>

## :clipboard: Contents
1. [About BlockToon](#🔎-about-blocktoon)
2. [Background](#🌱-background)
3. [Tech Stack](#📚-tech-stack)
4. [My Roles](#📝-my-roles)
5. [System Structure](#🏭-system-structure)  
</br>

## :mag_right: About BlockToon
BlockToon is a platform for Webtoon(an online comics) service based on Ethereum blockchain, and the InterPlanetary File System (IPFS).  
  
The goal of BlockToon is to improve existing Webtoon platforms.  
* Solving unfair profit distribution and contract issues  
* Managing information and data using decentralized storage: There is no central server. Maintenance costs can be saved.
* Uploading and managing are not imposed restrictions: It provides a flexible opportunity to the artists. 
* Proving copyright without other 3rd parties: Data can not be deleted or modified in the public blockchain. It makes clear the ownership.  

:movie_camera: <a href = "https://youtu.be/kUIFdpMKn84" target ="_blank">Demo Video</a>   
<br/> 
<p align = "center">
<img src = "images\AboutBlockToon1.png" width = "180" height = "300">
<img src = "images\AboutBlockToon2.png" width = "180" height = "300">
<img src = "images\AboutBlockToon3.png" width = "180" height = "300">
</p>
<br/> 

## :seedling: Background
BlockToon is a capston design project. The project duration was for 4 months from Februrary to May 2019.   
At the begining of this project, I studied blockchain technology and found out [UJO Music](https://blog.ujomusic.com/). UJO Music is a music platform based on Ethereum and IPFS.  
Meanwhile, my teammate usually read Webtoons in freetime between classes. I got an idea from UJO Music and my teammate.  
Subsequently, I proposed this project. :laughing:   

This project has awarded by Changwon National University faculty to the excellent capstone design project based on content and presentation.   

Related paper has published to The Korea Society of Computer and Information Conference, on July, 2019.   
:link: https://www.dbpia.co.kr/pdf/pdfView.do?nodeId=NODE08757679&mark=0&useDate=&ipRange=N&accessgl=Y&language=ko_KR&hasTopBanner=true  
<br/>

## :books: Tech Stack
<br/> 
<p align = "center">
<img src="https://img.shields.io/badge/Ethereum-3C3C3D?style=for-the-badge&logo=Ethereum&logoColor=white"> <img src="https://img.shields.io/badge/Solidity-363636?style=for-the-badge&logo=Solidity&logoColor=white"> <img src="https://img.shields.io/badge/IPFS-65C2CB?style=for-the-badge&logo=IPFS&logoColor=white"> <br/>
<img src="https://img.shields.io/badge/Androis Studio-3DDC84?style=for-the-badge&logo=Android Studio&logoColor=white"> <img src="https://img.shields.io/badge/Java-5282a1?style=for-the-badge&logo=Java&logoColor=white"> <img src="https://img.shields.io/badge/Web3j-f89820?style=for-the-badge&logo=Web3j&logoColor=white"> <br/>   
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"> <img src="https://img.shields.io/badge/PHP-777BB4?style=for-the-badge&logo=PHP&logoColor=white">
</p>
<br/>

## :memo: My roles 
* Android
   - Designing and implementing UI and launcher Icon 
   - Using Web3j, Implementing 'Adding Webtoon/Episodes', 'Getting Webtoon/Episodes and list', 'Payment', and 'Checking the balance of Ether' 
   - Using ImageView and PhotoView, Implementing 'Printing webtoon' that show a webtoon after getting images from IPFS network
   - Implementing LogIn function     
* Smart contract
    - Writing, reviewing, and deploying smart contract to Ropsten testnet
* IPFS
    - Setting environment for IPFS network
* Etc
    - Making PPT materials and presenting for project result reporting and the Capston design competition
    - Wrting paper

<br/>

## :factory: System Structure
BlockToon consists of the Ethereum blockchain network, IPFS, Android application, and MySQL Database.
<p align = "center">
<img src="images\SystemStructure.png" width ="500" height = "300">
</p>
<br/>

### Smart contract 
:link: https://github.com/Ha-youngPark/BlockToon_SmartContract   

Smart contract is written in Solidity.
We learned how to write solidity smart contract code from [Crypto Zombie](https://cryptozombies.io/).  
The contracts were deployed to Ropsten testnetwork.  
<br/>

#### Solidity and Compiler version
In solidity version 0.5.8, standard ABI encoder does not allow struct to be passed into DApps. So we used experimental ABIEncoverV2.  
In solidity version 0.8 and above, the ABIEncodereV2 is not experimental anymore.
```solidity
pragma solidity ^0.5.8;
pragma experimental ABIEncoderV2;
```

#### Structure
Two sturctures are defined.  
`struct Webtoon` has variables related with Webtoon data.  
`struct Episode` has variables related with Episode data.
```solidity
struct Webtoon{  
        string writer;  
        string webtoonTitle; 
        string webHashKey; // Hash value of thumbnail
        string writerAddress; 

    }

struct Episode{ 
      string episodeTitle; 
      string episodeHashkey; // Hash value of thumbnail
      string hashKey; // Hash value of Episode
      string money;  // Price 
    }
```

#### Functions
Ten functions are defined.  
Two functions are for adding Webtoon and Episode. Other functions are for querying data.
```solidity
// Add Webtoon
function addWebtoon(string memory _writer, string memory _webtoonTitle, string memory _webHashKey, string memory _writerAddress) public{...}

// Add Episode
function addEpisode(string memory _webtoonTitle, string memory _episodeTitle,string memory _episodeHashkey, string memory _hashKey, string memory _money) public{...}

// Query data
function Webtoon_getWriter() public view returns(string[] memory _writer){...}

function Webtoon_getWebtoonTitle() public view returns(string[] memory _webtoonTitle){...}

function Webtoon_getWebHashKey() public view returns(string[] memory _webhashKey){...}

function webtoon_getWriterAddress() public view returns(string[] memory _writerAddress){...}

function Eipsode_getEpisodeTitle(string memory _webtoonTitle) public view returns(string[] memory _episodeTitle){...}

function Episode_getEpisodeHashKey(string memory _webtoonTitle) public view returns(string[] memory _episodeHashKey){...}

function Episode_getHashKey(string memory _webtoonTitle) public view returns(string[] memory _hashKey){...}

function Episode_getMoney(string memory _webtoonTitle) public view returns(string[] memory _Money){...}
```
<br/>

### Android Studio
We thought a lot of people read Webtoon using their moblie phone when free time or riding public transportation.   
For accecssibility, we decided to develop a mobile apllication.
<br/>

#### UI
1. Design  
Used images are from Humble RPG Game Dev Bundle. :blush:  
Used fonts are 둥근모꼴 and Fixedsys.  

2. RecyclerView  
RecyclerView is used for printing a list of Webtoons and episodes.  
ListView can also be used to generate lists in Android Studio. However, when we tried the ListView, lagging occurred if the number of items was too large.  
This is due to the character of the ListView that repeats the creation and destruction of items when scrolling occurs.   
The RecyclerView improves the performance by recycling items. When we tried the RecyclerView, the lagging disappeared.  

    **`RecyclerViewAdapter_Book.java`**  
    ```java
    public class RecyclerViewAdapter_Book extends RecyclerView.Adapter<RecyclerViewAdapter_Book.MyViewHolder> {

        private Context mContext;
        private List<Book> mData;
        public static AlertDialog.Builder builder ;
        public static AlertDialog alert3 ;

        public RecyclerViewAdapter_Book(Context mContext, List<Book> mData) {
            this.mContext = mContext;
            this.mData = mData;

            builder = new AlertDialog.Builder(mContext,R.style.AlertDialogStyle);
            alert3 = builder.create();

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view;
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            view = mInflater.inflate(R.layout.cardview_item_book,viewGroup,false);
            return new MyViewHolder(view);
        }
    }
    ...
    ```  
<br/>

3. WebToon View   
PhotoView and Picasso are used to get Webtoon images from the IPFS network and print the images.  <br/><br/>
[The InterPlanetary File System (IPFS)](https://github.com/ipfs/ipfs) is decentralized decentralized file system for storing and accessing files, websites, applications, and data. In the IPFS network, nodes communicate Peer-to-Peer without a central server. The IPFS uses DHT routing like Bit torrent file exchange. If you register a file, you will have a hash value and will be able to access the data via the hash value and URL. Ethereum Swarm can be alternative for the IPFS. Large amounts of data can not be stored in blockchain and must be stored in external storage. We used the IPFS to save the Webtoon images by decentralized.  <br/><br/> 
[PhotoView](https://github.com/Baseflow/PhotoView) library helps to zoom in and out of images on Android. Usually, to draw images, ImageView is used. The PhotoView makes it easier to implement zoom in comparison to ImageView.  <br/><br/>
[Piscasso](https://github.com/square/picasso) is a library that easily handles the process of downloading images from the web and printing them to ImageView/PhotoView.
This library is used to import images from an IPFS network through URL.
Context, image URL, and the PhotoView that want to place the image are passed.   


    **`WebToonView_Activity.java`**  
    ```java
    public class WebToonView_Activity extends AppCompatActivity {

        Intent intent;
        String webHashKey;

        ImageView webtoonIMG;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_web_toon_view);
            intent = getIntent();
            webHashKey = intent.getStringExtra("webHashKey");
            Log.d("test",webHashKey);

            ...

            PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
            Picasso.get().load("https://ipfs.io/ipfs/"+webHashKey).into(photoView);
            
            ...
        }
        ...
    }
    ```
<br/>

#### Connection
1. Ethereum Blockchain Network   
We need to connect the Android application and the Ethereum network.
Ethereum nodes communicate via the JSON-RPC protocol.   
DApp rarely communicates directly using JSON/RPC, instead uses Web3 libraries. Such libraries include Web3.js and Ethers.js for Javascript and Web3j for Java.  <br/><br/>
[Web3j](https://github.com/web3j/web3j) 
library is a Java and Android library for working with Smart Contracts and the Ethereum blockchain.  
First, generate a smart contract function wrapper using the Web3j Command Line Tool from ABI file of the deployed smart contract, then import the wrapper into the project. The wrapper enable you to interact directly with all of a smart contract's methods via a generated wrapper object.  


    **`BlockToon.java`**
    ```java
    /**
     * <p>Auto generated code.
     * <p><strong>Do not modify!</strong>
     * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
     * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
     * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
     *
     * <p>Generated with web3j version 4.2.0.
     */
    public class BlockToon extends Contract {
        private static final String BINARY = "{\r\n"
                + "\t\"linkReferences\": {},\r\n"
                + "\t\"object\": \"608060405234801561001057600080fd5b50611703806100206000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c806393f08e7811610066..."
    ... 
    ```
    <br/>

    We created a `Web3j` file and wrote down the deployed smart contract's address.  
    This is used to load and interact with  the smart contract using Web3j and the wrapper.  

    **`Web3j.java`**
    ```java
    public class Web3j {
        //public static String contractAddress = "0xdc95a66e51521524a6a18f9511cc55ff543c8d35";
        public static  String contractAddress = "0x316e081ba3fa338359cc364bc3ddae56e88c975e";
    }
    ```
    <br/>
    
    We wrote 12 files that interact with the smart contract's functions that add/load data using Web3j and the wrapper.  
    Processes include: Connecting to Ethereum blockchain, Creating credentials, Loading a smart contract, and transacting with a smart contract.  
    We used [Infura](https://www.infura.io/) to connect to Ethereum blockchain.  

    **`Web3jAddWebtoon.java`**
    ```java
    /*Add Webtoon*/
    public class Web3jAddWebtoon extends AsyncTask<String,Void,Void> {
        
        ...

        protected Void doInBackground(String... args) {
            try {

                Web3j web3 = Web3j.build(new HttpService("https://ropsten.infura.io/v3/27fe5667c98041f7b79c924833f38de7")); // 
                // To send synchronous requests
                Web3ClientVersion web3ClientVersion = web3.web3ClientVersion().send();
                String clientVersion = web3ClientVersion.getWeb3ClientVersion();
                Credentials credentials = Credentials.create(args[0]); // Create Credentials
                BlockToon contract = BlockToon.load(com.example.BlockToon.Web3j.contractAddress, web3, credentials, GAS_PRICE, GAS_LIMIT); // Load smart contract

                contract.addWebtoon(args[1],args[2],args[3],args[4]).send(); // Transact with a smart contract
                // Writer name, Webtoon title, Thumbnail hash value, Writer address  


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        ...
    }
    ```
<br/>

2. MySQL Database   
In Android, Direct connections to the MySQL database are not supported.  
Our Android application is indirectly connected to the database through a web server and query files written in PHP.

    **`mysql.java`**
    ```java
    public class mysql extends AsyncTask<String, Void, String> {  

        protected String tempUserId;
        protected String cert;

        @Override
        protected String doInBackground(String... arg0) {

            String result = new String();

            try {
                String link = arg0[0];
                tempUserId = arg0[1];
                URL phpUrl = new URL(link);
                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();

                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            result = line;
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            cert = result;
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            cert = result;  // 1: exist, 0: null 
        }
    }
    ```
<br/>

### MySQL Database
MySQL database stores information necessary for membership registration and login.  
Member information is not stored on the blockchain because it needs to be modified and must not be public.  
We used MySQL which we learned in database class.

<p align = "center">
<img src="images\DatabaseTable.png" width ="350" height = "150">
</p>
<br/>

To connect MySQL database and the Android application, a total of five query files were written in PHP.    
:link: https://github.com/Ha-youngPark/BlockToon_PHP  

**`insertData.php`**
```php
<?php
$con=mysqli_connect("localhost","root","1234","test");
 
if (mysqli_connect_errno($con))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
mysqli_query($con, 'set names utf8');

$userId = $_GET['userId'];
$password = $_GET['password'];
$accountKey = $_GET['accountKey'];
$privateKey = $_GET['privateKey'];
$result = mysqli_query($con,"insert into test values('$userId','$password','$accountKey','$privateKey')");
$row = mysqli_fetch_array($result);
$data = $row[0];
 
if($data){
//$json_data = json_encode($data, JSON_UNESCAPED_UNICODE);
//echo $json_data;
echo $data;
}
mysqli_close($con);

?>
```
<br/>