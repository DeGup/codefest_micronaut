package micronaut.meetup;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import micronaut.meetup.model.Model;

@Controller("/restController")
public class RestController {

    private JpaService jpaService;
    private MeetupConfiguration meetupConfiguration;
    private Meetup meetup;

    public RestController(JpaService jpaService, MeetupConfiguration meetupConfiguration, Meetup meetup) {
        this.jpaService = jpaService;
        this.meetupConfiguration = meetupConfiguration;
        this.meetup = meetup;
    }

    @Get(uri = "/{id}", produces = "application/json")
    public HttpResponse<Model> index(@PathVariable("id") String id) {
        Model model = jpaService.getModel(id);
        if (model == null) {
            return HttpResponse.noContent();
        } else {
            return HttpResponse.ok(model);
        }
    }

    @Get(uri = "/property/hello", processes = "text/plain")
    public String hello() {
        return meetupConfiguration.getHelloWorld();
    }

    @Get(uri = "/property/meetup", processes = "text/plain")
    public String meetup() {
        return meetup.getName();
    }

    @Post(uri = "/{id}", consumes = "application/json", produces = "application/json")
    public HttpResponse<String> create(@PathVariable("id") String id) {
        jpaService.saveModel(id);
        return HttpResponse.accepted();
    }


}
