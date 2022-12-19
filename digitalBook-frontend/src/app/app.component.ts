import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from './_services/token-storage.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  private roles: string[] = [];
  isLoggedIn = false;
  showAuthorBoard = false;
  showReaderBoard = false;
  showGuestBoard = false;

  username?: string;
  // router: any;

  constructor(private tokenStorageService: TokenStorageService,private router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.getToken();

    if (this.isLoggedIn) {
      const user = this.tokenStorageService.getUser();
      this.roles = user.roles;

      this.showAuthorBoard = this.roles.includes('ROLE_AUTHOR');
      this.showReaderBoard = this.roles.includes('ROLE_READER');
      this.showGuestBoard = this.roles.includes('ROLE_GUEST');

      this.username = user.username;
    }
  }

  logout(): void {
    this.tokenStorageService.signOut();
    this.router.navigate(['/search']).then(() => {
      window.location.reload();
  });
}
}
