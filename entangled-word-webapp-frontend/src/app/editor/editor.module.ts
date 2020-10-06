import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EditorComponent} from './editor.component';
import {RouterModule} from '@angular/router';
import {AuthGuard} from '../services/auth-guard.service';


const editorRouting: ModuleWithProviders = RouterModule.forChild([
    {
        path: 'newpost',
        component: EditorComponent,
        canActivate: [AuthGuard]
    }
]);

@NgModule({
    declarations: [EditorComponent],
    imports: [
        editorRouting,
        CommonModule
    ]
})
export class EditorModule {
}
