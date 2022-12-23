import { Component, OnInit } from '@angular/core';
import * as internal from 'events';
import { TokenStorageService } from '../_services/token-storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-createbook',
  templateUrl: './createbook.component.html',
  styleUrls: ['./createbook.component.css']
})

export class CreatebookComponent implements OnInit {
 
  form : any = {
    title:null,
   publisher:null,
   category:null,
   price:null,
   content:null,
   publishedDate:null,
   logo:null
  };

  successMsg?:any;
  isSuccess=false;
  isCreateBookFailed = false;
  errorMessage?:string;
  userId: any;
 
  constructor(private userService: UserService,private tokenStorage: TokenStorageService) { }

    ngOnInit(): void {
   }

   public createBook(): void {
    const user = this.tokenStorage.getUser();
    this.userId = user.id;
    const { title,publisher,category,
      price,content, publishedDate,logo} = this.form;

    this.userService.getCreatedBooks(title,this.userId,publisher,category,
      price,content,publishedDate,logo).subscribe(
      data => {
        console.log(data);
        console.log(Object.values(data));
        this.isSuccess = true;
        this.isCreateBookFailed=false;
        this.successMsg=Object.values(data);
       
      },
      err => {
        this.errorMessage = err.error.message;
        // this.isSuccess = false;
        this.isCreateBookFailed=true;
      }
    );
  }
}