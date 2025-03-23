<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Service" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.3.0/css/bootstrap.min.css">
        <script src="https://kit.fontawesome.com/bf043842f3.js" crossorigin="anonymous"></script>
    </head>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            font-family: Verdana, sans-serif;
            margin:0;
        }
        img {
            vertical-align: middle;
        }
        .nav-link{
            color: white;
            text-decoration: none;
            padding: 15px;
            display: block;
            text-align: center;
        }
        .navbar h3 {
            font-family: 'Montserrat', sans-serif;
            margin: 0;
        }
        .navbar a {
            color: #fff;
            text-decoration: none;
        }
        .navbar a:hover {
            color: #d1d1d1;
        }
    </style>
    <body>
           <div class="w3-container w3-black w3-padding-32">
                <h1>Subscribe</h1>
                <p>To get special offers and VIP treatment:</p>
                <p><input class="w3-input w3-border" type="text" placeholder="Enter e-mail" style="width:100%"></p>
                <button type="button" class="w3-button w3-red w3-margin-bottom">Subscribe</button>
            </div>
            <footer class="w3-padding-64 w3-light-grey w3-small w3-center" id="footer">
                <div class="w3-row-padding">
                    <div class="w3-col s4">
                        <h4>Contact</h4>
                        <p>Questions? Go ahead.</p>
                        <form action="/action_page.php" target="_blank">
                            <p><input class="w3-input w3-border" type="text" placeholder="Name" name="Name" required></p>
                            <p><input class="w3-input w3-border" type="text" placeholder="Email" name="Email" required></p>
                            <p><input class="w3-input w3-border" type="text" placeholder="Subject" name="Subject" required></p>
                            <p><input class="w3-input w3-border" type="text" placeholder="Message" name="Message" required></p>
                            <button type="submit" class="w3-button w3-block w3-black">Send</button>
                        </form>
                    </div>

                    <div class="w3-col s4">
                        <h4>About</h4>
                        <p><a href="#">About us</a></p>
                        <p><a href="#">We're hiring</a></p>
                        <p><a href="#">Support</a></p>
                        <p><a href="#">Find store</a></p>
                        <p><a href="#">Shipment</a></p>
                        <p><a href="#">Payment</a></p>
                        <p><a href="#">Gift card</a></p>
                        <p><a href="#">Return</a></p>
                        <p><a href="#">Help</a></p>
                    </div>

                    <div class="w3-col s4 w3-justify">
                        <h4>Store</h4>
                        <p><i class="fa fa-fw fa-map-marker"></i> Company Name</p>
                        <p><i class="fa fa-fw fa-phone"></i> 0044123123</p>
                        <p><i class="fa fa-fw fa-envelope"></i> ex@mail.com</p>
                        <h4>We accept</h4>
                        <p><i class="fa fa-fw fa-cc-amex"></i> Amex</p>
                        <p><i class="fa fa-fw fa-credit-card"></i> Credit Card</p>
                        <br>
                        <i class="fa fa-facebook-official w3-hover-opacity w3-large"></i>
                        <i class="fa fa-instagram w3-hover-opacity w3-large"></i>
                        <i class="fa fa-snapchat w3-hover-opacity w3-large"></i>
                        <i class="fa fa-pinterest-p w3-hover-opacity w3-large"></i>
                        <i class="fa fa-twitter w3-hover-opacity w3-large"></i>
                        <i class="fa fa-linkedin w3-hover-opacity w3-large"></i>
                    </div>
                </div>
            </footer>

            <div class="w3-black w3-center w3-padding-24">Footer Contents Add later <a href="https://www.w3schools.com/w3css/default.asp" title="W3.CSS" target="_blank" class="w3-hover-opacity">CSS on W3</a></div>
       
    </body>
</html>
