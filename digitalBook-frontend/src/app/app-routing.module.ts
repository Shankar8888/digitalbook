import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { BoardModeratorComponent } from './board-moderator/board-moderator.component';
import { BoardAdminComponent } from './board-admin/board-admin.component';
import { SearchbookComponent } from './searchbook/searchbook.component';
import { CreatebookComponent } from './createbook/createbook.component';
import { SubscribedbooksComponent } from './subscribedbooks/subscribedbooks.component';
import { SubscribebookComponent } from './subscribebook/subscribebook.component';
import { BooksbyauthorComponent } from './booksbyauthor/booksbyauthor.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'user', component: BoardUserComponent },
  { path: 'mod', component: BoardModeratorComponent },
  { path: 'admin', component: BoardAdminComponent },
  { path: 'search', component: SearchbookComponent },
  { path: 'createbook', component: CreatebookComponent },
  { path: 'booksbyauthor', component: BooksbyauthorComponent},
  { path: 'subscribedbooks', component: SubscribedbooksComponent},
  { path: 'subscribebook', component: SubscribebookComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
