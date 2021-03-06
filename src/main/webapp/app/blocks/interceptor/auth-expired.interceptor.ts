import { JhiHttpInterceptor } from 'ng-jhipster';
import { RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Injector } from '@angular/core';
import { AuthServerProvider } from '../../shared/auth/auth-session.service';
import { StateStorageService } from '../../shared/auth/state-storage.service';
import { Router } from '@angular/router';
// import { LoginModalService } from '../../shared/login/login-modal.service';

export class AuthExpiredInterceptor extends JhiHttpInterceptor {

    constructor(private injector: Injector,
        private stateStorageService: StateStorageService,
                private router: Router ) {
        super();
    }

    requestIntercept(options?: RequestOptionsArgs): RequestOptionsArgs {
        return options;
    }

    responseIntercept(observable: Observable<Response>): Observable<Response> {
        return <Observable<Response>> observable.catch((error) => {
            console.log( 'In repsponse interceptor...' );
            if (error.status === 401 && error.text() !== '' && error.json().path && error.json().path.indexOf('/api/account') === -1) {
                console.log( 'Should be redirecting to the login screen...' );
                this.stateStorageService.loginFailureCode = error.json().message;
                console.log('Updated error code to ' + this.stateStorageService.loginFailureCode);
                const authServerProvider = this.injector.get(AuthServerProvider);
                const destination = this.stateStorageService.getDestinationState();
                const to = destination.destination;
                const toParams = destination.params;
                authServerProvider.logout();
                if (to.name === 'accessdenied') {
                    this.stateStorageService.storePreviousState(to.name, toParams);
                    console.log(this.stateStorageService.getPreviousState());
                }
                // const loginServiceModal = this.injector.get(LoginModalService);
                // TODO This should redirect to login component.
                // this.modalRef = this.loginModalService.open();
                this.router.navigate(['/login']);
            }
            return Observable.throw(error);
        });
    }
}
