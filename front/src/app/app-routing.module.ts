import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule) }, //permet de charger AuthModule qu'au besoin.
  { path: 'article', loadChildren: () => import('./features/article/article.module').then(m => m.ArticleModule) },
  { path: 'topic', loadChildren: () => import('./features/topic/topic.module').then(m => m.TopicModule) },
  { path: 'profile', loadChildren: () => import('./features/profile/profile.module').then(m => m.ProfileModule) },
  { path: '**', redirectTo: '/'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
