import { Component, OnInit } from '@angular/core';
import * as internal from 'events';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-createbook',
  templateUrl: './createbook.component.html',
  styleUrls: ['./createbook.component.css']
})

export class CreatebookComponent implements OnInit {
 
  form : any = {
    title:null,
   author:null,
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
 
  constructor(private userService: UserService) { }

    ngOnInit(): void {
   }

   public createBook(): void {
    const { title,author,publisher,category,
      price,content, publishedDate,logo} = this.form;

    this.userService.getCreatedBooks(title,author,publisher,category,
      price,content,publishedDate,logo).subscribe(
      data => {
        console.log(data);
        console.log(Object.values(data));
        this.successMsg=Object.values(data);
        this.isSuccess = true;
        this.isCreateBookFailed=false;
      },
      err => {
        this.errorMessage = err.error.message;
        // this.isSuccess = false;
        this.isCreateBookFailed=true;
      }
    );
  }
}

  //   book : any = {
  //     title:null,
  //    author:null,
  //    publisher:null,
  //    category:null,
  //    price:null,
  //    content:null,
  //    logo:null,
  //    publishedDate:null
  //   };
  //   errorMessage = '';
   
  //  public createBook(){
  //     const { title,author,publisher,category,
  //       price,content,
  //       publishedDate } = this.book;
  
  //     this.userService.getCreatedBooks(title,author,publisher,category,
  //       price,content,publishedDate).subscribe(
  //       data => {
  //         console.log(data);
  //         this.content=data;
  //         this.isSuccess = true;
  //         this.isCreateBookFailed=false;
  //       },
  //       err => {
  //         this.errorMessage = err.error.message;
  //         // this.isSuccess = false;
  //         this.isCreateBookFailed=true;
  //       }
  //     );
  //   }
  // }
 
 export interface bookCreated{
   id:number;
 }
 