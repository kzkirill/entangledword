import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {FeedComponent} from './feed/feed.component';
import {AuthComponent} from './auth/auth.component';

const routes: Routes = [
    {path: 'feed', component: FeedComponent},
    {path: 'login', component: AuthComponent},
    {path: '', redirectTo: '/feed', pathMatch: 'full'}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
