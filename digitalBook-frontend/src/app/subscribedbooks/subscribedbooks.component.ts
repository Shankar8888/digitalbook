import { Component, OnInit } from '@angular/core';
import { bookFound } from '../searchbook/searchbook.component';
// import { bookFound } from '../searchbook/searchbook.component';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-subscribedbooks',
  templateUrl: './subscribedbooks.component.html',
  styleUrls: ['./subscribedbooks.component.css']
})

export class SubscribedbooksComponent implements OnInit {
  

  constructor(private userService: UserService,private tokenStorage: TokenStorageService) { }

  content?:subscriptionFound[];
  userId?: any;
  successMsg?:String;
  bookObj?:any;

  isSuccessful = false;
  isSubscrFoundFailed = false;
  errorMessage?:string ;

  ngOnInit(): void {
  }


  public subscribedBooks(){
    const user=this.tokenStorage.getUser();
    this.userId=user.id;
    console.log(user);
    this.userService.getSubscriptions(this.userId).subscribe(
      data => {
        console.log(data);
        this.content = JSON.parse(data).subscriptionList;
        this.isSuccessful=true;
        this.isSubscrFoundFailed=false;
      },
      err => {
        console.log(err.error.message);
        // this.content = err.error.message;
        this.errorMessage = err.error.message;
        this.isSubscrFoundFailed=true;

      }
    );
  }

  isCancelSubscrFailed=false;

  public unsubscribeBook(_userId: any,_subscriptionId: any){
    this.userService.cancelSubscription(_userId,_subscriptionId).subscribe(
      data => {
        console.log(data);
        this.successMsg = JSON.parse(data).message;
        this.isCancelSubscrFailed=false;
      },
      err => {
        console.log(err);
        this.isCancelSubscrFailed=true;
        this.errorMessage = err.error.message;
      }
    );
  }

  isReadBookFailed=false;

  public readBook(_userId: any,_subscriptionId: any){
    this.userService.readBookContent(_userId,_subscriptionId).subscribe(
      data => {
        console.log(data);
        this.bookObj = JSON.parse(data).book;
        // this.isSuccessful=true;
        this.isReadBookFailed=false;
      },
      err => {
        console.log(err);
        // this.content = err.error.message;
        this.isReadBookFailed=true;
        this.errorMessage = err.error.message;
      }
    );
  }
}

export interface subscriptionFound{
  id:number;
  userId:number;
  book:bookFound;
  // id:number;
  // title:string;
  // author:string;
  // publisher:string;
  // category:string;
  // price:DoubleRange;
  // logo:string;
}


export interface bookContent{
  id:number;
  content:string;
}